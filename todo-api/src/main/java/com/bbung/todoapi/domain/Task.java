package com.bbung.todoapi.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "task")
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String contents;
    private String status;
    private int orders;
    private String importance;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;

}
