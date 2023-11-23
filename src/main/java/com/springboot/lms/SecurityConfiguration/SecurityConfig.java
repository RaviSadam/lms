package com.springboot.lms.SecurityConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.lms.Services.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity security) throws Exception{
        return security.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }

    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf((csrf)->
                csrf.disable()
            )
            .sessionManagement((session)->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .securityMatcher("/**")    
            .authorizeHttpRequests((auth)->
                auth
                    .requestMatchers("/","/register").permitAll()
                    .requestMatchers(HttpMethod.POST,"/app/**").permitAll()
                    .anyRequest().authenticated()
            );
        return http.build();
    }

}
