package com.bbung.todoapi.domain.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class BoardListDto {

    private Integer id;
    private String title;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
