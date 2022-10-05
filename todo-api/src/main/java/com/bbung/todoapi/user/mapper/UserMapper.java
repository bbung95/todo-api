package com.bbung.todoapi.user.mapper;

import com.bbung.todoapi.domain.User;
import com.bbung.todoapi.user.dto.UserDto;
import com.bbung.todoapi.user.dto.UserFormDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {

    int insertUser(User user);

    Optional<UserDto> findById(Integer id);

    Optional<User> loadByUsername(String username);

    int updatePassword(@Param("id") Integer id, @Param("password") String password);

    int updateNickname(@Param("id") Integer id, @Param("nickname") String nickname);
}
