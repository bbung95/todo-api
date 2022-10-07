package com.bbung.todoapi.task.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
@Builder
public class TaskFormDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    private String contents;
    @NotBlank
    private String importance;
    @NotBlank
    private Integer boardId;
}
