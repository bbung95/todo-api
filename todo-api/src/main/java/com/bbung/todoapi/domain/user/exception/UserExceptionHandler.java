package com.bbung.todoapi.domain.user.exception;

import com.bbung.todoapi.util.ExceptionMessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({
            DuplicationUsername.class,
            UserValidationException.class,
            BadCredentialsException.class,
            UsernameNotFoundException.class,
            UserUpdateTypeNotFoundException.class
    })
    public ResponseEntity badRequestExceptionHandler(RuntimeException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionMessageUtil.customErrorMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }
}
