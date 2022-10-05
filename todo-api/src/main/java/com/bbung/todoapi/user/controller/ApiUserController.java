package com.bbung.todoapi.user.controller;

import com.bbung.todoapi.config.security.UserInfo;
import com.bbung.todoapi.domain.User;
import com.bbung.todoapi.user.dto.UserDto;
import com.bbung.todoapi.user.dto.UserFormDto;
import com.bbung.todoapi.user.dto.UserLoginForm;
import com.bbung.todoapi.user.dto.UserUpdateFormDto;
import com.bbung.todoapi.user.exception.UserValidationException;
import com.bbung.todoapi.user.service.UserService;
import com.bbung.todoapi.util.JwtUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
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

    @Setter
    @Getter
    public class Token{
        private String token;
    }

    @PostMapping("login")
    public ResponseEntity loginUser(@RequestBody @Valid UserLoginForm userLoginForm, BindingResult result){

        if(result.hasErrors()){
            throw new UserValidationException(result);
        }

        UserInfo userInfo = userService.usernameAndPasswordCheck(userLoginForm);
        String token = jwtUtil.successAuthorizationToken(userInfo);

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities()));


        System.out.println("token = " + token);

        Token token1 = new Token();
        token1.setToken(token);

        return ResponseEntity.status(HttpStatus.OK).body(token1);
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
    public ResponseEntity updateUser(@PathVariable Integer id, @RequestBody UserUpdateFormDto userUpdateFormDto){

        userService.updateUser(id, userUpdateFormDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
