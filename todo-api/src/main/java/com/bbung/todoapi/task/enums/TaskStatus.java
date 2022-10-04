package com.bbung.todoapi.task.enums;

public enum TaskStatus {

    WAITING("대기"){},
    ACTIVATE("작업중"){},
    COMPLETION("완료"){};

    private String value;

    TaskStatus(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
