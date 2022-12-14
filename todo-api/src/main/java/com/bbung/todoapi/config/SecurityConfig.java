package com.bbung.todoapi.config;

import com.bbung.todoapi.config.security.JwtAuthorizationEntryPoint;
import com.bbung.todoapi.config.security.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthorizationFilter authorizationFilter;

    private final JwtAuthorizationEntryPoint jwtAuthorizationEnttyPoint;


    @Override
    public void configure(WebSecurity web) throws Exception {
        // static path 설정
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .headers().frameOptions().disable()
            .and()
                .httpBasic().disable()
                .formLogin().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthorizationEnttyPoint)
            .and()
                .authorizeRequests()
                .antMatchers("/login", "/sign", "/", "/board/**").permitAll()
                .antMatchers("/api/user/login", "/api/user").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
        ;
    }
}
