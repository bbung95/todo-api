package com.bbung.todoapi.task.service;

import com.bbung.todoapi.common.PageResponse;
import com.bbung.todoapi.domain.Task;
import com.bbung.todoapi.task.dto.*;
import com.bbung.todoapi.task.enums.TaskStatus;
import com.bbung.todoapi.task.exception.TaskNotFoundException;
import com.bbung.todoapi.task.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final int DEFAULT_ORDER = 1;
    private final TaskMapper taskMapper;
    private final ModelMapper modelMapper;

    public int saveTask(TaskFormDto taskForm){

        Task task = modelMapper.map(taskForm, Task.class);
        Integer orders = taskMapper.findLastOrder().orElse(DEFAULT_ORDER);
        task.setOrders(orders);
        task.setStatus(TaskStatus.WAITING.name());

        taskMapper.insertTask(task);

        return task.getId();
    }

    public TaskDto findTask(int id) {

        Optional<TaskDto> findTask = taskMapper.findById(id);
        if(!findTask.isPresent()){
            throw new TaskNotFoundException(Integer.toString(id));
        }
        return findTask.get();
    }

    public PageResponse findTaskList(TaskSearchParam param) {

        List<TaskListDto> taskList = taskMapper.findTaskList(param);
        int totalCount = taskMapper.findTaskTotalCount(param);

        return PageResponse.<TaskListDto>builder()
                .list(taskList)
                .totalCount(totalCount)
                .build();
    }

    public void updateTask(int id, TaskFormDto formDto) {

        Task task = modelMapper.map(formDto, Task.class);
        task.setId(id);

        taskMapper.updateTask(task);
    }

    public void updateTaskStatusOrImportance(int id, TaskUpdateFormDto taskData) {

        findTask(id);

        if(taskData.getTarget().equals("status")){
            taskMapper.updateTaskStatus(id, taskData.getValue());
        }else if(taskData.getTarget().equals("importance")){
            taskMapper.updateTaskImportance(id, taskData.getValue());
        }
    }

    public void deleteTask(int id) {
        findTask(id);
        taskMapper.deleteTask(id);
    }
}
