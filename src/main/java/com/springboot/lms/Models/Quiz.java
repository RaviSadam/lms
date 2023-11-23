package com.springboot.lms.Models;

import java.sql.Date;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="quiz")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {
    @Id
    @SequenceGenerator(
        name="quiz_id_generator",
        sequenceName = "quiz_id_generator",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "quiz_id_generator"
    )
    @Column(name="quiz_id")
    private long id;

    @Column(name="quiz_name",length = 50)
    private String quizName;

    @Column(name="start_date")
    @JsonProperty("start-date")
    private Date startDate;

    @Column(name="end_date")
    @JsonProperty("end_date")
    private Date endDate; 


    @ManyToMany(mappedBy = "quizes")
    @JsonBackReference
    private List<User> users;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(
        name="course_id",
        referencedColumnName = "course_id"
    )
    @JsonManagedReference
    private Course course;

    @OneToMany(mappedBy = "quizes",fetch = FetchType.EAGER)
    @JsonBackReference
    private List<Question> questions;
}
