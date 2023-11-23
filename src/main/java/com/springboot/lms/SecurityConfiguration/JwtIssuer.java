package com.springboot.lms.SecurityConfiguration;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtIssuer {

    public String issue(long id,String email,List<String> roles){
        return JWT.create()
                    .withSubject(String.valueOf(id))
                    .withExpiresAt(Instant.now().plus(Duration.of(1,ChronoUnit.DAYS)))
                    .withIssuedAt(Instant.now())
                    .withClaim("email", email)
                    .withClaim("roles", roles)
                    .sign(Algorithm.HMAC256(new String("secret")));
                    

    }   
}
