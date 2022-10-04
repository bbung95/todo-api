package com.bbung.todoapi.task.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class TaskUpdateFormDto {

    private String target;
    private String value;
}
