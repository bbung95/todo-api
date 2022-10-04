package com.bbung.todoapi.task.mapper;

import com.bbung.todoapi.domain.Task;
import com.bbung.todoapi.task.dto.TaskDto;
import com.bbung.todoapi.task.dto.TaskListDto;
import com.bbung.todoapi.task.dto.TaskSearchParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TaskMapper {

    int insertTask (Task task);

    Optional<TaskDto> findById(int id);

    List<TaskListDto> findTaskList(TaskSearchParam param);

    int updateTask(Task task);

    int deleteTask(int id);

    int updateTaskStatus(@Param("id") int id, @Param("status") String status);

    int updateTaskImportance(@Param("id") int id, @Param("importance") String importance);

    Optional<Integer> findLastOrder();

}
