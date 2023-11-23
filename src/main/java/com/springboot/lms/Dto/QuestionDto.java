package com.springboot.lms.Dto;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
    
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class QuestionDto {
    private long id;
    
    private String question;
    
    private Set<String> options;

    @JsonProperty("correct_option")
    private String correctOption;

    @JsonProperty("selected_option")
    private String selectedOption;   

}
