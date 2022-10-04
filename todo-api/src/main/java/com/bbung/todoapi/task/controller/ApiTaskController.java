package com.bbung.todoapi.task.controller;


import com.bbung.todoapi.domain.Task;
import com.bbung.todoapi.task.dto.*;
import com.bbung.todoapi.task.service.TaskService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/task")
public class ApiTaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity saveTask(@RequestBody @Valid TaskFormDto taskForm, Errors result){

//        if(result.hasErrors()){
//
////            result.getAllErrors().stream().forEach(item ->
////                    System.out.println(item));
//
//        }

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

        List<TaskListDto> taskList = taskService.findTaskList(param);

        return ResponseEntity.ok().body(taskList);
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
