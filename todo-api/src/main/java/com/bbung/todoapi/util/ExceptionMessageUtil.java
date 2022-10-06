package com.bbung.todoapi.util;

import com.bbung.todoapi.common.CustomErrorMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;

public class ExceptionMessageUtil {

    public static String messageParse(BindingResult result){

        ObjectMapper objectMapper = new ObjectMapper();

        String message = "";

        for (FieldError fieldError : result.getFieldErrors()) {
            message += fieldError.getDefaultMessage();
        }

        return message;
    }

    public static CustomErrorMessage customErrorMessage(int statusCode, String messages){
        return new CustomErrorMessage(statusCode, getMessageList(messages));
    }

    private static List<String> getMessageList(String messages){

        String[] split = messages.split(",");

        return Arrays.asList(split);
    }
}
