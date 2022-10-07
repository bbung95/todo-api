package com.bbung.todoapi.domain.board.exception;

import com.bbung.todoapi.util.ExceptionMessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BoardExceptionHandler {

    @ExceptionHandler({
            BoardValidationException.class,
            BoardNotFoundException.class
    })
    public ResponseEntity badRequestExceptionHandler(RuntimeException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessageUtil.customErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }
}
