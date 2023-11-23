package com.springboot.lms.ErrorAndExceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserAlreadyExists extends RuntimeException{
    private String message;
}
