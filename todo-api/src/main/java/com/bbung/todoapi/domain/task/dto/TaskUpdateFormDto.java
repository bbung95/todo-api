package com.bbung.todoapi.domain.task.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
@Builder
public class TaskUpdateFormDto {

    @NotBlank(message = "수정할 필드값이 존재하지 않습니다.")
    private String type;
    @NotBlank(message = "수정값을 입력해주세요.")
    private String value;
}
