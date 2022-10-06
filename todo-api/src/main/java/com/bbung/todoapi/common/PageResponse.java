package com.bbung.todoapi.common;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageResponse<T> {

    private int totalCount;
    private List<T> list = new ArrayList<>();

}
