package com.springboot.lms.SecurityConfiguration;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.var;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtDecoder jwtDecoder;
    private final JwtToPriencipalConverter jwtToPriencipalConverter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        extractTokenFromHreader(request)
                        .map(jwtDecoder::decode)
                        .map(jwtToPriencipalConverter::convert)
                        .map(UserPrincipalAuthenticationToken::new)
                        .ifPresent(authentication->SecurityContextHolder.getContext().setAuthentication(authentication));
        filterChain.doFilter(request, response);
    }

    private Optional<String>  extractTokenFromHreader(HttpServletRequest request){
        var token=request.getHeader("Authorization");
        if(token!=null && token.startsWith("Bearer ")){
            return Optional.of(token.substring(7));
        }
        return Optional.empty();
    }
    
}
