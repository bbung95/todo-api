package com.bbung.todoapi.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bbung.todoapi.config.security.UserInfo;
import com.bbung.todoapi.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtil {

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.subject}")
    private String subject;

    public String successAuthorizationToken(UserInfo user){

        String token = JWT.create()
                .withClaim("uesrname", user.getUsername())
                .withClaim("nickname", user.getNickname())
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis()+(10 * 6 * 60000)))
                .sign(Algorithm.HMAC512(key))
                ;

        return "Bearer " + token;
    }
}
