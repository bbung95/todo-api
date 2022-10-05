package com.bbung.todoapi.user.service;

import com.bbung.todoapi.config.security.UserInfo;
import com.bbung.todoapi.domain.User;
import com.bbung.todoapi.user.dto.UserDto;
import com.bbung.todoapi.user.dto.UserFormDto;
import com.bbung.todoapi.user.dto.UserLoginForm;
import com.bbung.todoapi.user.enums.UserRole;
import com.bbung.todoapi.user.exception.DuplicationUsername;
import com.bbung.todoapi.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        user.setRole(UserRole.MEMBER.name());

        return userMapper.insertUser(user);
    }

    public UserDto findUser(Integer id){

        UserDto findUserDto = userMapper.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(Integer.toString(id)));

        return findUserDto;
    }
}