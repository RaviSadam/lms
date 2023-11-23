package com.springboot.lms.Models;

import java.sql.Date;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @Column(name="course_id")
    @JsonProperty("course_id")
    private long id;

    @Column(name="course_name",length = 50)
    @JsonProperty("course_name")
    private String courseName;

    @Column(name="course_code",length=30)
    @JsonProperty("course_code")
    private String courseCode;


    @Column(name="enrollment_capacity",columnDefinition = "INT DEFAULT 50")
    @JsonProperty("enrollment_capacity")
    private int enrollmentCapacity;

    @Column(name="start_date")
    @JsonProperty("start-date")
    private Date startDate;

    @Column(name="end_date")
    @JsonProperty("end_date")
    private Date endDate;

    @Column(columnDefinition = "INT DEFAULT 90")
    private int duration;

    @ManyToMany(mappedBy = "courses",fetch = FetchType.LAZY)
    @JsonBackReference
    private List<User> users; 
    

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="dep_id",referencedColumnName = "department_id")
    @JsonManagedReference
    private Department department;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "course",fetch = FetchType.LAZY)
    @JsonBackReference
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Quiz> quizs;

}
