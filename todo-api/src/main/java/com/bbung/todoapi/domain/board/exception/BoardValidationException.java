package com.bbung.todoapi.domain.board.exception;

import com.bbung.todoapi.util.ExceptionMessageUtil;
import org.springframework.validation.BindingResult;

public class BoardValidationException extends RuntimeException {
    public BoardValidationException(BindingResult result) {
        super(ExceptionMessageUtil.messageParse(result));
    }
}
