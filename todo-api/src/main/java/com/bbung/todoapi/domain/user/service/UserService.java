package com.bbung.todoapi.domain.user.service;

import com.bbung.todoapi.config.security.UserInfo;
import com.bbung.todoapi.entity.User;
import com.bbung.todoapi.domain.user.dto.UserUpdateFormDto;
import com.bbung.todoapi.domain.user.enums.UserRole;
import com.bbung.todoapi.domain.user.enums.UserUpdateType;
import com.bbung.todoapi.domain.user.exception.UserUpdateTypeNotFoundException;
import com.bbung.todoapi.domain.user.mapper.UserMapper;
import com.bbung.todoapi.domain.user.dto.UserDto;
import com.bbung.todoapi.domain.user.dto.UserFormDto;
import com.bbung.todoapi.domain.user.dto.UserLoginForm;
import com.bbung.todoapi.domain.user.exception.DuplicationUsername;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserMapper userMapper;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserInfo loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userMapper.loadByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        UserInfo userInfo = modelMapper.map(user, UserInfo.class);

        return userInfo;
    }

    public UserInfo usernameAndPasswordCheck(UserLoginForm loginForm){

        User user = userMapper.loadByUsername(loginForm.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(loginForm.getUsername()));

        if(!passwordEncoder.matches(loginForm.getPassword(), user.getPassword())){
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        UserInfo userInfo = modelMapper.map(user, UserInfo.class);

        return userInfo;
    }

    public int saveUser(UserFormDto userForm){

        userMapper.loadByUsername(userForm.getUsername()).ifPresent(user -> {
            throw new DuplicationUsername(userForm.getUsername());
        });

        userForm.setPassword(passwordEncoder.encode(userForm.getPassword()));
        User user = modelMapper.map(userForm, User.class);
        if(user.getRole() == null){
            user.setRole(UserRole.MEMBER.name());
        }

        return userMapper.insertUser(user);
    }

    public UserDto findUser(Integer id){

        UserDto findUserDto = userMapper.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(Integer.toString(id)));

        return findUserDto;
    }

    public int updateUser(Integer id, UserUpdateFormDto userUpdateFormDto){

        userUpdateTypeCheck(userUpdateFormDto.getType());

        int result = 0;

        if(userUpdateFormDto.getType().equals("password")){
            String password = passwordEncoder.encode(userUpdateFormDto.getValue());
            userMapper.updatePassword(id, password);
        }else if (userUpdateFormDto.getType().equals("nickname")){
            userMapper.updateNickname(id, userUpdateFormDto.getValue());
        }

        return result;
    }

    private void userUpdateTypeCheck(String type) {

        boolean typeCheck = Arrays.stream(UserUpdateType.values()).filter(item -> item.getValue().equals(type))
                .findAny().isPresent();

        if(!typeCheck){
            throw new UserUpdateTypeNotFoundException(type);
        }
    }

}
