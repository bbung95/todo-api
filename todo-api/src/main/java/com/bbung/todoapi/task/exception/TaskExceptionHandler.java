package com.bbung.todoapi.task.exception;

import com.bbung.todoapi.util.ExceptionMessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskExceptionHandler{

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity notFoundErrorHandler(RuntimeException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionMessageUtil.customErrorMessage(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(TaskValidationException.class)
    public ResponseEntity badRequestErrorHandler(RuntimeException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessageUtil.customErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }
}