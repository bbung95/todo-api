package com.bbung.todoapi.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bbung.todoapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Value("${jwt.key}")
    private String key;

    private final UserService userService;

    private List<String> EXCLUDE_URL = Collections.unmodifiableList(
            Arrays.asList("/api/user/login", "/api/user", "/login", "/sign", "/", "/h2-console", "/board"));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("권한 및 인증 체크");

        String jwtToken = request.getHeader("Authorization");

        String username = null;

        if(jwtToken != null && jwtToken.startsWith("Bearer ")){

            jwtToken = jwtToken.replace("Bearer ", "").trim();

            username = JWT.require(Algorithm.HMAC512(key)).build().verify(jwtToken).getClaim("username").asString();
        }

        log.info("URL = {}", request.getServletPath());
        log.info("username = {}", username);

        if(username != null){
            UserInfo userInfo = userService.loadUserByUsername(username);

            Authentication authentication = new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return EXCLUDE_URL.stream().anyMatch(item -> item.equalsIgnoreCase(request.getServletPath()));
    }
}
