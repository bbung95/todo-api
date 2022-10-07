package com.bbung.todoapi.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class BoardFormDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
}
