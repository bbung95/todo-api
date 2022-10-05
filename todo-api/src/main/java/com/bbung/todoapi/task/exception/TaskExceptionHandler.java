package com.bbung.todoapi.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskExceptionHandler{

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity notFoundErrorHandler(RuntimeException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(TaskValidationException.class)
    public ResponseEntity badRequestErrorHandler(RuntimeException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
