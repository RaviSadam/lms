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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name="role")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Roles {
    @Id
    @JsonProperty("role_id")
    @Column(name="role_id")
    private int id;

    @Column(name="role_name")
    @JsonProperty("role_name")
    private String roleName;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonBackReference
    private List<User> users;    
}
