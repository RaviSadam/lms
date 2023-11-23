package com.springboot.lms.Dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseRegister {
    @JsonAlias({"courseids","course_ids"})
    private List<Long> courseIds;
}
