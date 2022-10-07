package com.bbung.todoapi.domain.task.exception;

import com.bbung.todoapi.util.ExceptionMessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class TaskSearchTypeNotFoundException extends RuntimeException{

    public TaskSearchTypeNotFoundException(String message) {
        super(message);
    }
}