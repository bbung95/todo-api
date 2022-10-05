package com.bbung.todoapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExceptionMessageUtil {

    public static String messageParse(BindingResult result){

        ObjectMapper objectMapper = new ObjectMapper();

        Stream<String> stringStream = result.getFieldErrors().stream().map(item -> item.getDefaultMessage());

        try {
            return objectMapper.writeValueAsString(stringStream.collect(Collectors.toList()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
