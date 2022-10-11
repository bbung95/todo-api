package com.bbung.todoapi.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class Board {

    private Integer id;
    private String title;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Integer writer;
}
