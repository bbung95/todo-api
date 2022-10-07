package com.bbung.todoapi.domain.board.dto;

import com.bbung.todoapi.common.SearchParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BoardSearchParam extends SearchParam {

    private Integer writer;
}
