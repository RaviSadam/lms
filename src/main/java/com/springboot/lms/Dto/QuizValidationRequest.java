package com.springboot.lms.Dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizValidationRequest{
    private long id;
    @JsonAlias({"selected_option","selectedoption"})
    private String selectedOption;
}