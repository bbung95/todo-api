package com.bbung.todoapi.user.controller;

import com.bbung.todoapi.user.dto.UserFormDto;
import com.bbung.todoapi.user.mapper.UserMapper;
import com.bbung.todoapi.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/truncate.sql")
class ApiUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("User 회원가입 Mock 테스트")
    public void userSignMockTest() throws Exception {

        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setUsername("bbung");
        userFormDto.setPassword("1234");
        userFormDto.setNickname("rai");

        mockMvc.perform(post("/api/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(userFormDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("User 회원가입시 아이디 또는 비밀번호를 입력하지 않았을 경우 테스트")
    public void userSignMockBadRequestTest() throws Exception {

        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setNickname("rai");

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userFormDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("User 데이터 조회 테스트")
    public void findUserTest() throws Exception {

        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setUsername("bbung");
        userFormDto.setPassword("1234");
        userFormDto.setNickname("rai");

        int id = userService.saveUser(userFormDto);

        mockMvc.perform(get("/api/user/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("bbung"))
                .andExpect(jsonPath("nickname").value("rai"));
    }

    @Test
    @DisplayName("User 데이터가 존재하지 않을 경우 테스트")
    public void findUserNotFoundTest() throws Exception {

        mockMvc.perform(get("/api/user/{id}", 20))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}