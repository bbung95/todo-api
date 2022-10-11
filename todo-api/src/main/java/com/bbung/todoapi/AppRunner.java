package com.bbung.todoapi;

import com.bbung.todoapi.domain.user.dto.UserFormDto;
import com.bbung.todoapi.domain.user.enums.UserRole;
import com.bbung.todoapi.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {

    private final UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        UserFormDto userFormDto = new UserFormDto();
        userFormDto.setUsername("admin");
        userFormDto.setPassword("1234");
        userFormDto.setNickname("admin");
        userFormDto.setRole(UserRole.ADMIN.name());

        userService.saveUser(userFormDto);
    }
}
