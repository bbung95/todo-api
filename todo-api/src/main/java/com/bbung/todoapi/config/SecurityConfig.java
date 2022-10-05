package com.bbung.todoapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/api/user/login").permitAll()
//                .antMatchers("/api/user").permitAll()
//                .anyRequest().authenticated();
                .anyRequest().permitAll();
    }
}
