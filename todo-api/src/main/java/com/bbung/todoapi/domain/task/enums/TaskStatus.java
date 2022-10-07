package com.bbung.todoapi.domain.task.enums;

public enum TaskStatus {

    ACTIVATE("작업중"),
    COMPLETION("완료");

    private String value;

    TaskStatus(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
