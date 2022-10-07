package com.bbung.todoapi.domain.user.exception;

import com.bbung.todoapi.util.ExceptionMessageUtil;
import org.springframework.validation.BindingResult;

public class UserValidationException extends RuntimeException {
    public UserValidationException(BindingResult result) {
        super(ExceptionMessageUtil.messageParse(result));
    }

}
