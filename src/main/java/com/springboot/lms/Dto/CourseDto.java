package com.springboot.lms.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CourseDto {
    private int duration;
    private String courseName; 
    private String endDate;
    private String startDate;
    private int enrollmentCapacity;
    private String courseCode;
}
