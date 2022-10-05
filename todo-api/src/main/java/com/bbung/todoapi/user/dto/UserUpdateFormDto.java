package com.bbung.todoapi.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class UserUpdateFormDto {

    private String type;
    private String value;
}
