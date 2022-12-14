package com.bbung.todoapi.domain.task.dto;

import com.bbung.todoapi.common.SearchParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TaskSearchParam extends SearchParam {

    private String importance;
    private Integer boardId;
    private String condition;
}
