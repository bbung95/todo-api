package com.bbung.todoapi.user.mapper;

import com.bbung.todoapi.Entity.User;
import com.bbung.todoapi.domain.user.dto.UserDto;
import com.bbung.todoapi.domain.user.mapper.UserMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Sql("/truncate.sql")
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    @DisplayName("User 등록 테스트")
    public void insertTest() throws Exception {

        User user = User.builder()
                .username("bbung")
                .nickname("rai")
                .password("1234")
                .build();

        int result = userMapper.insertUser(user);

        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("User PK 상세보기 테스트")
    public void findByIdTest() throws Exception {

        User user = saveUser();

        UserDto findUser = userMapper.findById(user.getId()).get();

        assertThat(findUser.getUsername()).isEqualTo("bbung");
        assertThat(findUser.getNickname()).isEqualTo("rai");
    }

    @Test
    @DisplayName("User 아이디 상세보기 테스트")
    public void loadByUsernameTest() throws Exception {

        User user = saveUser();

        User findUser = userMapper.loadByUsername(user.getUsername()).get();

        assertThat(findUser.getUsername()).isEqualTo("bbung");
        assertThat(findUser.getNickname()).isEqualTo("rai");
        assertThat(findUser.getPassword()).isEqualTo("1234");
    }

    @Test
    @DisplayName("User 패스워드 변경 테스트")
    public void updatePasswordTest() throws Exception {
        User user = saveUser();

        int result = userMapper.updatePassword(user.getId(), "12345");

        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("User 이름 변경 테스트")
    public void updateNicknameTest() throws Exception {
        User user = saveUser();

        userMapper.updateNickname(user.getId(), "bbung!!");
        UserDto findUserDto = userMapper.findById(user.getId()).get();

        assertThat(findUserDto.getNickname()).isEqualTo("bbung!!");
    }

    private User saveUser() {
        User user = User.builder()
                .username("bbung")
                .nickname("rai")
                .password("1234")
                .build();

        userMapper.insertUser(user);

        return user;
    }
}