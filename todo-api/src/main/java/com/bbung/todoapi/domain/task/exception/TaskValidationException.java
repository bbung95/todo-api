package com.bbung.todoapi.domain.task.exception;

import com.bbung.todoapi.util.ExceptionMessageUtil;
import org.springframework.validation.BindingResult;

public class TaskValidationException extends RuntimeException {

    public TaskValidationException(BindingResult result) {
        super(ExceptionMessageUtil.messageParse(result));
    }

}
