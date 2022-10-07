package com.bbung.todoapi.domain.task.exception;

import com.bbung.todoapi.util.ExceptionMessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskExceptionHandler{

    @ExceptionHandler({
            TaskValidationException.class,
            TaskTypeNotFoundException.class,
            TaskNotFoundException.class,
            TaskValueNotFoundException.class
    })
    public ResponseEntity badRequestErrorHandler(RuntimeException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessageUtil.customErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }
}