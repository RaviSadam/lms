package com.springboot.lms.SecurityConfiguration;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.springboot.lms.Models.User;

import lombok.var;

@Component
public class JwtToPriencipalConverter {
    public User convert(DecodedJWT jwt){
        return User.builder()
                .email(jwt.getClaim("email").asString())
                .id(Integer.parseInt(jwt.getSubject()))
                .userAuthorities(getUserAuthorities(jwt))
                .build();
    }
    private List<SimpleGrantedAuthority> getUserAuthorities(DecodedJWT jwt){
        var claim=jwt.getClaim("roles");
        if(claim.isNull() || claim.isMissing())
            return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
