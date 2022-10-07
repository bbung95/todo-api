package com.bbung.todoapi.domain.task.enums;

public enum TaskSearchType {

    TITLE("title"),
    CONTENTS("contents");

    private String value;

    TaskSearchType(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
