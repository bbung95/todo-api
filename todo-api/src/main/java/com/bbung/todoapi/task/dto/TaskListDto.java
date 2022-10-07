package com.bbung.todoapi.task.dto;

import com.bbung.todoapi.task.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Arrays;

@Setter
@Getter
@ToString
public class TaskListDto {

    private Integer id;
    private String title;
    private String contents;
    private String status;
    private String importance;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private int orders;

    public void setStatus(String status){
        this.status = Arrays.stream(TaskStatus.values()).filter(item -> item.name().equals(status))
                .findAny().get().getValue();
    }
}
