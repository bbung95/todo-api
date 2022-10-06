package com.bbung.todoapi.user.exception;

import com.bbung.todoapi.common.CustomErrorMessage;
import com.bbung.todoapi.util.ExceptionMessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({DuplicationUsername.class, UserValidationException.class, BadCredentialsException.class})
    public ResponseEntity badRequestExceptionHandler(RuntimeException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessageUtil.customErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity notFoundExceptionHandler(RuntimeException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ExceptionMessageUtil.customErrorMessage(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

}
