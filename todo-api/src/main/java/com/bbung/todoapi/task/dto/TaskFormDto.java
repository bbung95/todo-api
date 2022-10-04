package com.bbung.todoapi.task.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class TaskFormDto {

    private String title;
    private String contents;
    private String importance;
}
