package com.bbung.todoapi.domain.user.exception;

public class UserUpdateTypeNotFoundException extends RuntimeException {
    public UserUpdateTypeNotFoundException(String type) {
        super("해당 타입 " + type + "이 존재하지 않습니다.");
    }
}
