package com.springboot.lms.Repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.lms.Dto.RoleProjection;
import com.springboot.lms.Dto.UserProjection;
import com.springboot.lms.Models.User;

import java.util.List;


public interface UserRepo extends JpaRepository<User,Long>{
    
    //for authentication purpose gives only id,email,password from DB
    @Query("SELECT u.id AS id,u.email AS email,u.password AS password FROM User u WHERE u.email=:email")
    public UserProjection findUserByEmail(@Param("email") String email);

    //gives user by id
    public User findById(long id);

    //gives user roles
    @Query("SELECT r.id AS id,r.roleName AS roleName FROM User u JOIN u.roles r WHERE u.id=:id")
    public List<RoleProjection> findUserRolesById(@Param("id") long id);


    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleName='STUDENT'")
    public List<User> findAllStudents(Pageable page);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleName='FACULTY'")
    public List<User> findAllFaculty(Pageable page);
} 
