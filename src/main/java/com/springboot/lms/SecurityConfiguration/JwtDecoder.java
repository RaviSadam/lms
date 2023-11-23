package com.springboot.lms.SecurityConfiguration;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtDecoder {
    public DecodedJWT decode(String token){
        return JWT.require(Algorithm.HMAC256(new String("secret"))) 
                .build()
                .verify(token);
    }     
}
