package com.bbung.todoapi.domain.user.enums;

public enum UserUpdateType {

    PASSWORD("password"),
    NICKNAME("nickname");

    private String value;

    UserUpdateType(String value) {

        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
