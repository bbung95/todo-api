package com.bbung.todoapi.user.exception;

public class DuplicationUsername extends RuntimeException {

    public DuplicationUsername(String message) {
        super("해당 ID " + message + "가 중복됩니다.");
    }

}
