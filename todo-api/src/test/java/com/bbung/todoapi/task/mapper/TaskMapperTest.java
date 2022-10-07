package com.bbung.todoapi.task.mapper;

import com.bbung.todoapi.Entity.Task;
import com.bbung.todoapi.domain.task.dto.TaskDto;
import com.bbung.todoapi.domain.task.dto.TaskListDto;
import com.bbung.todoapi.domain.task.dto.TaskSearchParam;
import com.bbung.todoapi.domain.task.enums.TaskImportance;
import com.bbung.todoapi.domain.task.enums.TaskStatus;
import com.bbung.todoapi.domain.task.mapper.TaskMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import javax.sql.DataSource;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("/truncate.sql")
class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("JDBC Connection 테스트")
    public void connectionTest() throws Exception {

        try(Connection connection = dataSource.getConnection()) {
            System.out.println("URL = " + connection.getMetaData().getURL());
            System.out.println("UserName = " + connection.getMetaData().getUserName());
        }
    }

    @Test
    @DisplayName("Todo 등록 테스트")
    public void insetTaskTest() throws Exception {

        Task task = Task.builder()
                .contents("test")
                .title("test")
                .orders(1)
                .createDate(LocalDateTime.of(2022,10,04,11,06))
                .status(TaskStatus.ACTIVATE.name())
                .importance(TaskImportance.HIGH.name())
                .build();

        taskMapper.insertTask(task);

        assertThat(task.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Todo 상세보기 테스트")
    public void findTaskTest() throws Exception {

        Task task = getTask(1);

        taskMapper.insertTask(task);
        TaskDto findTask = taskMapper.findById(task.getId()).get();

        assertThat(findTask.getTitle()).isEqualTo("test");
        assertThat(findTask.getContents()).isEqualTo("test");
        assertThat(findTask.getStatus()).isEqualTo(TaskStatus.ACTIVATE.name());
        assertThat(findTask.getImportance()).isEqualTo(TaskImportance.HIGH.name());
    }

    @Test
    @DisplayName("Todo 목록 조회 테스트")
    public void findTaskListTest() throws Exception {

        for(int i = 1; i <= 10; i++){
            Task task = getTask(i);
            taskMapper.insertTask(task);
        }

        TaskSearchParam param = new TaskSearchParam();
        List<TaskListDto> taskList = taskMapper.findTaskList(param);

        assertThat(taskList.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("Todo 업데이트 테스트")
    public void updateTaskTest() throws Exception {

        Task task = getTask(1);
        taskMapper.insertTask(task);

        task.setTitle("update");
        task.setContents("updateContents");

        taskMapper.updateTask(task);

        TaskDto findTask = taskMapper.findById(task.getId()).get();

        assertThat(findTask.getTitle()).isEqualTo("update");
        assertThat(findTask.getContents()).isEqualTo("updateContents");
    }

    @Test
    @DisplayName("Todo 삭제 테스트")
    public void deleteTaskTest() throws Exception {

        findTaskListTest();
        taskMapper.deleteTask(1);

        TaskSearchParam param = new TaskSearchParam();
        List<TaskListDto> taskList = taskMapper.findTaskList(param);

        assertThat(taskList.size()).isEqualTo(9);
    }

    @Test
    @DisplayName("Todo 상태 업데이트 테스트")
    public void updateTaskStatusTest() throws Exception {

        Task task = getTask(1);
        taskMapper.insertTask(task);

        taskMapper.updateTaskStatus(task.getId(), TaskStatus.COMPLETION.name());

        TaskDto findTask = taskMapper.findById(task.getId()).get();

        assertThat(findTask.getStatus()).isEqualTo(TaskStatus.COMPLETION.name());
    }

    @Test
    @DisplayName("Todo 중요도 업데이트 테스트")
    public void updateTaskImportance() throws Exception {

        Task task = getTask(1);
        taskMapper.insertTask(task);

        taskMapper.updateTaskImportance(task.getId(), TaskImportance.LOW.name());

        TaskDto findTask = taskMapper.findById(task.getId()).get();

        assertThat(findTask.getImportance()).isEqualTo(TaskImportance.LOW.name());
    }

    @Test
    @DisplayName("Todo 마지막 순번 테스트")
    public void findTaskLastOrdersTest() throws Exception {

        findTaskListTest();

        int lastOrder = taskMapper.findLastOrder(TaskImportance.LOW.name()).orElse(1);

        assertThat(lastOrder).isEqualTo(10);

    }

    private Task getTask(int order) {
        Task task = Task.builder()
                .contents("test")
                .title("test")
                .orders(order)
                .status(TaskStatus.ACTIVATE.name())
                .importance(TaskImportance.HIGH.name())
                .build();
        return task;
    }
}