package com.bbung.todoapi.task.controller;

import com.bbung.todoapi.common.PageResponse;
import com.bbung.todoapi.task.dto.*;
import com.bbung.todoapi.task.exception.TaskValidationException;
import com.bbung.todoapi.task.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/task")
@Slf4j
public class ApiTaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity saveTask(@RequestBody @Valid TaskFormDto taskForm, BindingResult result){

        if(result.hasErrors()){
            throw new TaskValidationException(result);
        }

        int id = taskService.saveTask(taskForm);

        return ResponseEntity.ok().body(id);
    }

    @GetMapping("{id}")
    public ResponseEntity findTask(@PathVariable int id){

        TaskDto task = taskService.findTask(id);

        return ResponseEntity.ok().body(task);
    }

    @GetMapping
    public ResponseEntity findTaskList(TaskSearchParam param){

        PageResponse result = taskService.findTaskList(param);

        log.info("TaskList = {}", result);

        return ResponseEntity.ok().body(result);
    }

    @PutMapping("{id}")
    public ResponseEntity updateTask(@PathVariable int id, @RequestBody TaskFormDto formDto){

        taskService.updateTask(id, formDto);

        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    @PatchMapping("{id}")
    public ResponseEntity updateStatus(@PathVariable int id, @RequestBody TaskUpdateFormDto taskData){

        taskService.updateTaskStatusOrImportance(id, taskData);

        return ResponseEntity.status(HttpStatus.FOUND).build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteTask(@PathVariable int id){

        taskService.deleteTask(id);

        return ResponseEntity.ok().build();
    }


}
