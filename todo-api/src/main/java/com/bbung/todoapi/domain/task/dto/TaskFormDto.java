package com.bbung.todoapi.domain.task.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
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
    @Min(value = 1, message = "boardId가 존재하지 않습니다.")
    private Integer boardId;
}
