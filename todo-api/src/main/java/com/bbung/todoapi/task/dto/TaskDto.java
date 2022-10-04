package com.bbung.todoapi.task.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class TaskDto {

    private Integer id;
    private String title;
    private String contents;
    private String status;
    private String importance;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

}
