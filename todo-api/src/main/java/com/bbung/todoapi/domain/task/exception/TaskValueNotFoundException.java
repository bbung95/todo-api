package com.bbung.todoapi.domain.task.exception;

import com.bbung.todoapi.util.ExceptionMessageUtil;
import org.springframework.validation.BindingResult;

public class TaskValueNotFoundException extends RuntimeException {

    public TaskValueNotFoundException(String message) {
        super("해당 " + message + " 가 올바르지 않습니다.");
    }

}
