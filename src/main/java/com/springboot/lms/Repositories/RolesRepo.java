package com.springboot.lms.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.springboot.lms.Models.Roles;

public interface RolesRepo extends JpaRepository<Roles,Integer> {

    public Roles findByRoleName(String roleName);
    
} 