package com.bbung.todoapi.entity;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private LocalDateTime createDate;
    private String role;

}
