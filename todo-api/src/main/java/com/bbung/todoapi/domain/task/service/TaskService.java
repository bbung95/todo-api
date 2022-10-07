package com.bbung.todoapi.domain.task.service;

import com.bbung.todoapi.domain.board.service.BoardService;
import com.bbung.todoapi.common.PageResponse;
import com.bbung.todoapi.Entity.Task;
import com.bbung.todoapi.domain.task.dto.*;
import com.bbung.todoapi.domain.task.enums.TaskSearchType;
import com.bbung.todoapi.domain.task.enums.TaskUpdateType;
import com.bbung.todoapi.domain.task.exception.TaskNotFoundException;
import com.bbung.todoapi.domain.task.enums.TaskImportance;
import com.bbung.todoapi.domain.task.enums.TaskStatus;
import com.bbung.todoapi.domain.task.exception.TaskTypeNotFoundException;
import com.bbung.todoapi.domain.task.exception.TaskValueNotFoundException;
import com.bbung.todoapi.domain.task.mapper.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final int DEFAULT_ORDER = 1;
    private final TaskMapper taskMapper;
    private final BoardService boardService;
    private final ModelMapper modelMapper;

    public int saveTask(TaskFormDto taskForm){

        Task task = modelMapper.map(taskForm, Task.class);
        Integer orders = taskMapper.findLastOrder(taskForm.getImportance()).orElse(DEFAULT_ORDER);
        task.setOrders(orders);
        task.setStatus(TaskStatus.ACTIVATE.name());

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

    public Map findTaskList(TaskSearchParam param) {

        boardCheck(param.getBoardId());
        taskSearchTypeCheck(param.getSearchType());

        Map map = new HashMap<String, PageResponse<TaskListDto>>();

        Arrays.stream(TaskImportance.values()).forEach(importance -> {

            param.setImportance(importance.name());
            List<TaskListDto> taskList = taskMapper.findTaskList(param);
            int totalCount = taskMapper.findTaskTotalCount(param);

            PageResponse<TaskListDto> pageResponse = PageResponse.<TaskListDto>builder()
                    .list(taskList)
                    .totalCount(totalCount)
                    .build();

            map.put(importance.name(), pageResponse);
        });

        return map;
    }

    public void updateTask(int id, TaskFormDto formDto) {

        Task task = modelMapper.map(formDto, Task.class);
        task.setId(id);
        taskMapper.updateTask(task);
    }

    public void updateTaskStatusOrImportance(int id, TaskUpdateFormDto taskData) {

        findTask(id);
        taskUpdateTypeCheck(taskData.getType());

        if(taskData.getType().equals(TaskUpdateType.STATUS.getValue())){
            updateStatus(id, taskData);
        }else if(taskData.getType().equals(TaskUpdateType.IMPORTANCE.getValue())){
            updateImportance(id, taskData);
        }
    }

    private void updateStatus(int id, TaskUpdateFormDto taskData) {
        taskStatusCheck(taskData.getValue());
        taskMapper.updateTaskStatus(id, taskData.getValue());
    }

    private void updateImportance(int id, TaskUpdateFormDto taskData){
        taskImportanceCheck(taskData.getValue());
        taskMapper.updateTaskImportance(id, taskData.getValue());
    }

    public void deleteTask(int id) {
        findTask(id);
        taskMapper.deleteTask(id);
    }

    private void boardCheck(Integer boardId){
        boardService.findBoard(boardId);
    }

    private void taskUpdateTypeCheck(String type){
        boolean typeCheck = Arrays.stream(TaskUpdateType.values())
                .filter(item -> item.getValue().equals(type))
                .findAny().isPresent();

        if(!typeCheck){
            throw new TaskTypeNotFoundException(type);
        }
    }

    private void taskStatusCheck(String value){
        boolean typeCheck = Arrays.stream(TaskStatus.values())
                .filter(item -> item.name().equals(value))
                .findAny().isPresent();

        if(!typeCheck){
            throw new TaskValueNotFoundException(value);
        }
    }

    private void taskImportanceCheck(String value){
        boolean typeCheck = Arrays.stream(TaskImportance.values())
                .filter(item -> item.name().equals(value))
                .findAny().isPresent();

        if(!typeCheck){
            throw new TaskValueNotFoundException(value);
        }
    }

    private void taskSearchTypeCheck(String searchType){
        boolean typeCheck = Arrays.stream(TaskSearchType.values())
                .filter(item -> item.getValue().equals(searchType))
                .findAny().isPresent();

        if(!typeCheck){
            throw new TaskTypeNotFoundException(searchType);
        }
    }
}
