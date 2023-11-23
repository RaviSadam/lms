package com.springboot.lms.Repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.lms.Models.Question;

public interface QuestionRepo extends JpaRepository<Question,Long> {
    
    @Query("SELECT q FROM Question q WHERE q.id IN :ids")
    public List<Question> getQuestionCorrectOption(@Param("ids") Set<Long> ids);
    
}
