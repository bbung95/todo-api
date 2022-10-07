package com.bbung.todoapi.domain.task.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(String message) {
        super("해당 ID " + message + "의 Task가 존재하지않습니다.");
    }
}
