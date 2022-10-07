package com.bbung.todoapi.domain.user.controller;

import com.bbung.todoapi.config.security.UserInfo;
import com.bbung.todoapi.domain.user.dto.UserDto;
import com.bbung.todoapi.domain.user.dto.UserFormDto;
import com.bbung.todoapi.domain.user.dto.UserLoginForm;
import com.bbung.todoapi.domain.user.dto.UserUpdateFormDto;
import com.bbung.todoapi.domain.user.exception.UserValidationException;
import com.bbung.todoapi.domain.user.service.UserService;
import com.bbung.todoapi.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class ApiUserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @PostMapping("login")
    public ResponseEntity loginUser(@RequestBody @Valid UserLoginForm userLoginForm, BindingResult result){

        if(result.hasErrors()){
            throw new UserValidationException(result);
        }

        UserInfo userInfo = userService.usernameAndPasswordCheck(userLoginForm);
        Object token = jwtUtil.successAuthorizationToken(userInfo);

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities()));

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PostMapping
    public ResponseEntity signUser(@RequestBody @Valid UserFormDto userFormDto, BindingResult result){

        if(result.hasErrors()){
            throw new UserValidationException(result);
        }

        int id = userService.saveUser(userFormDto);

        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @GetMapping("{id}")
    public ResponseEntity findUser(@PathVariable Integer id){

        UserDto user = userService.findUser(id);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PatchMapping("{id}")
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody @Valid UserUpdateFormDto userUpdateFormDto, BindingResult result){

        if(result.hasErrors()){
            throw new UserValidationException(result);
        }

        userService.updateUser(id, userUpdateFormDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
