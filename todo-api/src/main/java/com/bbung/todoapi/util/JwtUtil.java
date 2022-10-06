package com.bbung.todoapi.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bbung.todoapi.config.security.UserInfo;
import com.bbung.todoapi.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.subject}")
    private String subject;

    private final ObjectMapper objectMapper;

    public ObjectNode successAuthorizationToken(UserInfo user){

        String token = "Bearer " + JWT.create()
                .withClaim("username", user.getUsername())
                .withClaim("nickname", user.getNickname())
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis()+(10 * 6 * 60000)))
                .sign(Algorithm.HMAC512(key))
                ;

        ObjectNode tokenObject = objectMapper.createObjectNode().put("token", token);

        return tokenObject;
    }
}
