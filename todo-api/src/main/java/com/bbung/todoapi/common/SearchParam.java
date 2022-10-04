package com.bbung.todoapi.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Setter
@Getter
@ToString
public class SearchParam {

    private String keyword;
    private String searchType;
}
