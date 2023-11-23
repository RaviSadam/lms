package com.springboot.lms.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.lms.Models.Department;

public interface DepartmentRepo extends JpaRepository<Department,Integer> {

    @Query("SELECT d FROM Department d WHERE d.departmentCode=:code")
    public Department findDepByCode(@Param("code") String code);
    
}
