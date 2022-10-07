package com.bbung.todoapi.domain.task.enums;

public enum TaskUpdateType {

    IMPORTANCE("importance"),
    STATUS("status");

    private String value;

    TaskUpdateType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
