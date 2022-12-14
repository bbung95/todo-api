package com.bbung.todoapi.task.controller;

import com.bbung.todoapi.domain.task.dto.TaskFormDto;
import com.bbung.todoapi.domain.task.dto.TaskSearchParam;
import com.bbung.todoapi.domain.task.dto.TaskUpdateFormDto;
import com.bbung.todoapi.domain.task.enums.TaskImportance;
import com.bbung.todoapi.domain.task.enums.TaskStatus;
import com.bbung.todoapi.domain.task.enums.TaskUpdateType;
import com.bbung.todoapi.domain.task.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/truncate.sql")
class ApiTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    public void token() throws Exception {
//
//        UserLoginForm userLoginForm = new UserLoginForm();
//        userLoginForm.setUsername("admin");
//        userLoginForm.setPassword("1234");
//
//        String contentAsString = mockMvc.perform(post("/api/user/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(userLoginForm)))
//                .andDo(print())
//                .andReturn().getResponse().getContentAsString();
//
//        System.out.println("token = " + contentAsString);
//
////        return null;
//    }

    @Test
    @DisplayName("Task ?????? ?????????")
    public void saveTaskMockTest() throws Exception {

        TaskFormDto taskForm = getTaskFormDto();

        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskForm)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Task ???????????? ?????????")
    public void findTaskMockTest() throws Exception {

        TaskFormDto taskForm = getTaskFormDto();

        int id = taskService.saveTask(taskForm);

        mockMvc.perform(get("/api/task/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("title").value("mock test"))
                .andExpect(jsonPath("contents").value("mock test contents"));
    }

    @Test
    @DisplayName("Task ???????????? ???????????? ????????? NotFound ?????????")
    public void findTask_NotFoundMockTest() throws Exception {

        mockMvc.perform(get("/api/task/{id}", 100))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Task ?????? ???????????? ?????????")
    public void findTaskListMockTest() throws Exception {

        for(int i = 1; i <= 100; i++){
            TaskFormDto taskFormDto = getTaskFormDto();
            taskService.saveTask(taskFormDto);
        }

        TaskSearchParam param = new TaskSearchParam();
        param.setKeyword("mock");
        param.setSearchType("title");

        mockMvc.perform(get("/api/task")
                        .content(objectMapper.writeValueAsString(param)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Task ????????? ???????????? ?????????")
    public void updateTaskMockTest() throws Exception {

        TaskFormDto taskFormDto = getTaskFormDto();
        int id = taskService.saveTask(taskFormDto);

        taskFormDto.setTitle("mock update test");
        taskFormDto.setContents("mock update test content");

        mockMvc.perform(put("/api/task/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskFormDto)))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("Task ?????? ???????????? ?????????")
    public void updateStatusMockTest() throws Exception {

        TaskFormDto taskFormDto = getTaskFormDto();
        int id = taskService.saveTask(taskFormDto);

        TaskUpdateFormDto data = TaskUpdateFormDto.builder()
                .type(TaskUpdateType.STATUS.name())
                .value(TaskStatus.COMPLETION.name())
                .build();

        mockMvc.perform(patch("/api/task/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("Task ?????? ??????????????? ???????????? ????????? NotFount ?????????")
    public void updateStatus_NotFoundMockTest() throws Exception {

        TaskUpdateFormDto data = TaskUpdateFormDto.builder()
                .type(TaskUpdateType.STATUS.name())
                .value(TaskStatus.COMPLETION.name())
                .build();

        mockMvc.perform(patch("/api/task/{id}", 20)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Task ????????? ???????????? ?????????")
    public void updateImportanceMockTest() throws Exception {

        TaskFormDto taskFormDto = getTaskFormDto();
        int id = taskService.saveTask(taskFormDto);

        TaskUpdateFormDto data = TaskUpdateFormDto.builder()
                .type(TaskUpdateType.IMPORTANCE.name())
                .value(TaskImportance.LOW.name())
                .build();

        mockMvc.perform(patch("/api/task/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("Task ????????? ??????????????? ???????????? ????????? NotFount ?????????")
    public void updateImportance_NotFoundMockTest() throws Exception {

        TaskUpdateFormDto data = TaskUpdateFormDto.builder()
                .type(TaskUpdateType.IMPORTANCE.name())
                .value(TaskStatus.COMPLETION.name())
                .build();

        mockMvc.perform(patch("/api/task/{id}", 20)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Task ?????? ?????????")
    public void deleteTaskMockTest() throws Exception {

        for(int i = 1; i <= 20; i++){
            TaskFormDto taskFormDto = getTaskFormDto();
            taskService.saveTask(taskFormDto);
        }

        mockMvc.perform(delete("/api/task/{id}", 10))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Task ?????? ???????????? ???????????? ????????? NotFound ?????????")
    public void deleteTask_NotFoundMockTest() throws Exception {

        mockMvc.perform(delete("/api/task/{id}", 10))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private TaskFormDto getTaskFormDto() {
        TaskFormDto taskForm = TaskFormDto.builder()
                .title("mock test")
                .contents("mock test contents")
                .importance(TaskImportance.LOW.name())
                .build();
        return taskForm;
    }
}