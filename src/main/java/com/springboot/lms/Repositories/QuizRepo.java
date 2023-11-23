package com.springboot.lms.Repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.lms.Dto.StudentScoreProjection;
import com.springboot.lms.Models.Quiz;

public interface QuizRepo extends JpaRepository<Quiz,Long> {
    @Query("SELECT u.id AS is, u.firstName AS firstName,u.lastName AS lastName,u.rollNumber AS rollNumber FROM Quiz q JOIN q.users u WHERE  q.id=:id")
    public List<StudentScoreProjection> getUserAttemptedQuiz(@Param("id")long id,Pageable page);

    public Quiz findById(long id);
}
