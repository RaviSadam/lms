package com.springboot.lms.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.lms.Models.Course;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course,Long>{

    @Query("SELECT c FROM Course c WHERE c.id IN :codes")
    public List<Course> findAllCoursesByIds(@Param("codes") List<Long> ids);

    @Query("SELECT c FROM Course c JOIN c.users u WHERE u.id=:id")
    public List<Course> findAllCoursesByUserId(@Param("id") long id);

    public Course findByCourseCode(String code);
    
}
