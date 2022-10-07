package com.bbung.todoapi.task.controller;

import com.bbung.todoapi.domain.task.dto.TaskFormDto;
import com.bbung.todoapi.domain.task.dto.TaskSearchParam;
import com.bbung.todoapi.domain.task.dto.TaskUpdateFormDto;
import com.bbung.todoapi.domain.task.enums.TaskImportance;
import com.bbung.todoapi.domain.task.enums.TaskStatus;
import com.bbung.todoapi.domain.task.service.TaskService;
import com.bbung.todoapi.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/truncate.sql")
class ApiTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Task 등록 테스트")
    public void saveTaskMockTest() throws Exception {

        TaskFormDto taskForm = getTaskFormDto();

        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskForm)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Task 상세보기 테스트")
    public void findTaskMockTest() throws Exception {

        TaskFormDto taskForm = getTaskFormDto();

        int id = taskService.saveTask(taskForm);

        mockMvc.perform(get("/api/task/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("mock test"))
                .andExpect(jsonPath("contents").value("mock test contents"));
    }

    @Test
    @DisplayName("Task 상세보기 데이터가 없을때 NotFound 테스트")
    public void findTask_NotFoundMockTest() throws Exception {

        mockMvc.perform(get("/api/task/{id}", 100))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Task 목록 가져오기 테스트")
    public void findTaskListMockTest() throws Exception {

        for(int i = 1; i <= 100; i++){
            TaskFormDto taskFormDto = getTaskFormDto();
            taskService.saveTask(taskFormDto);
        }

        TaskSearchParam param = new TaskSearchParam();
        param.setKeyword("mock");
        param.setSearchType("title");

        mockMvc.perform(get("/api/task")
                        .content(objectMapper.writeValueAsString(param)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Task 데이터 업데이트 테스트")
    public void updateTaskMockTest() throws Exception {

        TaskFormDto taskFormDto = getTaskFormDto();
        int id = taskService.saveTask(taskFormDto);

        taskFormDto.setTitle("mock update test");
        taskFormDto.setContents("mock update test content");

        mockMvc.perform(put("/api/task/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskFormDto)))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("Task 상태 업데이트 테스트")
    public void updateStatusMockTest() throws Exception {

        TaskFormDto taskFormDto = getTaskFormDto();
        int id = taskService.saveTask(taskFormDto);

        TaskUpdateFormDto data = TaskUpdateFormDto.builder()
                .target("status")
                .value(TaskStatus.COMPLETION.name())
                .build();

        mockMvc.perform(patch("/api/task/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("Task 상태 업데이트시 데이터가 없을때 NotFount 테스트")
    public void updateStatus_NotFoundMockTest() throws Exception {

        TaskUpdateFormDto data = TaskUpdateFormDto.builder()
                .target("status")
                .value(TaskStatus.COMPLETION.name())
                .build();

        mockMvc.perform(patch("/api/task/{id}", 20)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Task 중요도 업데이트 테스트")
    public void updateImportanceMockTest() throws Exception {

        TaskFormDto taskFormDto = getTaskFormDto();
        int id = taskService.saveTask(taskFormDto);

        TaskUpdateFormDto data = TaskUpdateFormDto.builder()
                .target("importance")
                .value(TaskImportance.LOW.name())
                .build();

        mockMvc.perform(patch("/api/task/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("Task 중요도 업데이트시 데이터가 없을때 NotFount 테스트")
    public void updateImportance_NotFoundMockTest() throws Exception {

        TaskUpdateFormDto data = TaskUpdateFormDto.builder()
                .target("importance")
                .value(TaskStatus.COMPLETION.name())
                .build();

        mockMvc.perform(patch("/api/task/{id}", 20)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Task 삭제 테스트")
    public void deleteTaskMockTest() throws Exception {

        for(int i = 1; i <= 20; i++){
            TaskFormDto taskFormDto = getTaskFormDto();
            taskService.saveTask(taskFormDto);
        }

        mockMvc.perform(delete("/api/task/{id}", 10))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Task 삭제 테스트시 데이터가 없을때 NotFound 테스트")
    public void deleteTask_NotFoundMockTest() throws Exception {

        mockMvc.perform(delete("/api/task/{id}", 10))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private TaskFormDto getTaskFormDto() {
        TaskFormDto taskForm = TaskFormDto.builder()
                .title("mock test")
                .contents("mock test contents")
                .importance(TaskImportance.LOW.name())
                .build();
        return taskForm;
    }
}