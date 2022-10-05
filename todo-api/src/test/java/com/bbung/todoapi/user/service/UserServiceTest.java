package com.bbung.todoapi.user.service;

import com.bbung.todoapi.config.security.UserInfo;
import com.bbung.todoapi.user.dto.UserFormDto;
import com.bbung.todoapi.user.exception.DuplicationUsername;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("/truncate.sql")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("User 등록 테스트")
    public void userInsertTest() throws Exception {

        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setUsername("bbung");
        userFormDto.setPassword("1234");
        userFormDto.setNickname("rai");

        int result = userService.saveUser(userFormDto);

        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("User 등록시 아이디 중복 테스트")
    public void usernameDuplicateTest() throws Exception {

        String username = "bbung";

        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setUsername(username);
        userFormDto.setPassword("1234");
        userFormDto.setNickname("rai");

        userService.saveUser(userFormDto);

        DuplicationUsername duplicationUsername = assertThrows(DuplicationUsername.class, () -> {
            userService.saveUser(userFormDto);
        });

        assertThat(duplicationUsername.getMessage()).isEqualTo("해당 ID " + username + "가 중복됩니다.");
    }

    @Test
    @DisplayName("User 로그인시 아이디 찾기 테스트")
    public void userLoginServiceTest() throws Exception {

        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setUsername("bbung");
        userFormDto.setPassword("1234");
        userFormDto.setNickname("rai");

        userService.saveUser(userFormDto);

        UserInfo userInfo = userService.loadUserByUsername("bbung");

        assertThat(userInfo.getUsername()).isEqualTo("bbung");
        assertThat(userInfo.getRole()).isEqualTo("MEMBER");
        assertThat(userInfo.getNickname()).isEqualTo("rai");
    }

    @Test
    @DisplayName("User 로그인시 아이디 NotFound 테스트")
    public void userLoginServiceNotFoundTest() throws Exception {

        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setUsername("bbung");
        userFormDto.setPassword("1234");
        userFormDto.setNickname("rai");

        userService.saveUser(userFormDto);

        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("rai");
        });

        assertThat(usernameNotFoundException.getMessage()).isEqualTo("rai");
    }
}