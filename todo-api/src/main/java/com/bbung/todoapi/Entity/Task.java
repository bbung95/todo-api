package com.bbung.todoapi.Entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
public class Task {

    private Integer id;
    private String title;
    private String contents;
    private String status;
    private Integer orders;
    private String importance;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Integer boardId;
}
