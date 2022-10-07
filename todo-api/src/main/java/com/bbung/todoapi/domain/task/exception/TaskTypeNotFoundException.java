package com.bbung.todoapi.domain.task.exception;

public class TaskTypeNotFoundException extends RuntimeException{

    public TaskTypeNotFoundException(String message) {
        super("해당 " + message + " 타입이 존재하지않습니다.");
    }
}