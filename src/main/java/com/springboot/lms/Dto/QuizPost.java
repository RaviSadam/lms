package com.springboot.lms.Dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.springboot.lms.Models.Question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizPost {
    @JsonAlias({"quizname","quiz_name"})
    private String name;

    @JsonAlias({"coursecode","course_code"})
    private String courseCode;
    
    @JsonAlias({"start_date","startdate"})
    private String startDate;

    @JsonAlias({"end_date","enddate"})
    private String endDate;

   private List<Question> questions;
}
