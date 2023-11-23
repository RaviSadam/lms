package com.springboot.lms.ErrorAndExceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class InvalidUserData extends RuntimeException {
    private String message;
}
