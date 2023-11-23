package com.springboot.lms.Models;

import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="department")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {

    @Id
    @JsonProperty("department_id")
    @Column(name="department_id")
    private int id;

    @Column(name="department_code",length=15,nullable = false,unique = true)
    private String departmentCode;

    @Column(name="department_name",length=50,nullable = false,unique = true)
    private String departmentName;
    

    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY)
    @JsonBackReference
    @Fetch(value = FetchMode.SUBSELECT)
    private List<User> users;

    @OneToMany(mappedBy = "department",fetch = FetchType.LAZY)
    @JsonBackReference
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Course> courses;
}
