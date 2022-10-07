package com.bbung.todoapi;

import com.bbung.todoapi.user.dto.UserFormDto;
import com.bbung.todoapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {

    private final UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setUsername("admin");
        userFormDto.setPassword("admin1234");
        userFormDto.setNickname("admin");
        
        userService.saveUser(userFormDto);
    }
}
