package com.springboot.lms.Models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="question")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @SequenceGenerator(
        name="question_id_generator",
        sequenceName = "question_id_generator",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "question_id_generator"

    )
    @Column(name="question_id")
    @JsonProperty("question_id")
    private long id;

    @JsonProperty("question_name")
    @Column(name="question_name",length = 40,nullable = false,unique = true)
    private String questionName;

    @Column(length = 512)
    private String question;

    @ElementCollection
    @CollectionTable(
        name="options",
        joinColumns = @JoinColumn(name="id")
    )
    private List<String> options=new ArrayList<>();

    @Column(name="correct_option",nullable = false)
    @JsonProperty("correct_option")
    private String correctOption;

    @Column(columnDefinition = "INT DEFAULT 10")
    int score;

    @Column(columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String explanation;

    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name="quiz_id",
        referencedColumnName = "quiz_id"
    )
    @JsonManagedReference
    private Quiz quizes;
}
