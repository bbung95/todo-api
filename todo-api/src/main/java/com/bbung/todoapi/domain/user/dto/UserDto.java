package com.bbung.todoapi.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class UserDto {

    private Integer id;
    private String username;
    private String nickname;
    private LocalDateTime createDate;

}
