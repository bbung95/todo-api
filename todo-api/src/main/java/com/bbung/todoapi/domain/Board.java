package com.bbung.todoapi.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Entity
@Table(name = "board")
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private Integer writer;
}
