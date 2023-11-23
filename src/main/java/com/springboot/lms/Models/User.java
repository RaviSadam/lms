package com.springboot.lms.Models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Fetch;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User implements UserDetails {

    @Id
    @SequenceGenerator(
        name="user_id_generator",
        sequenceName = "user_id_generator",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "user_id_generator"

    )
    @Column(name="user_id")
    @JsonProperty("user_id")
    private long id;

    @Column(name="first_name",length = 50)
    @JsonProperty("first_name")
    private String firstName;

    @Column(name="last_name",length = 50)
    @JsonProperty("last_name")
    private String lastName;

    @Column(name="roll_number",length = 20)
    @JsonProperty("roll_number")
    private String rollNumber;

    @Column(name="email")
    private String email;

    @JsonIgnore
    private String password;

    private int year;

    //user ------- Many-to-Many------Roles
    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name = "user_roles",
        joinColumns = {
            @JoinColumn(
                name="user_id",
                referencedColumnName = "user_id"
            )
        },
        inverseJoinColumns = {
            @JoinColumn(
                name="role_id",
                referencedColumnName = "role_id"
            )
        }
    )
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<Roles> roles;



    //User-------Many-to-Many-------course
    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name="user_courses",
        joinColumns = {
            @JoinColumn(name="user_id",referencedColumnName = "user_id")
        },
        inverseJoinColumns = {
            @JoinColumn(name="course_id",referencedColumnName = "course_id")
        }
    )
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<Course> courses;


    //user------Many-To-One-----Department
    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JoinColumn(
        name="dep_id",
        referencedColumnName ="department_id"    
    )
    @JsonManagedReference
    private Department department;


    //User-------Many-to-Many------Quiz
    @ManyToMany(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JoinTable(
        name="user_quiz",
        joinColumns = {
            @JoinColumn(name="user_id",referencedColumnName = "user_id")
        },
        inverseJoinColumns = {
            @JoinColumn(name="quiz_id",referencedColumnName = "quiz_id")
        }
    )
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<Quiz> quizes;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userAuthorities;
    }


    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public String getUsername() {
        return this.email;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
       return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }

    @Transient
    private List<SimpleGrantedAuthority> userAuthorities;


    @Transient
    public List<String> getRolesOfUser(){
        List<String> roles=new ArrayList<>();
        for(SimpleGrantedAuthority simpleGrantedAuthority:this.userAuthorities){
            roles.add(simpleGrantedAuthority.getAuthority());
        }
        return roles;
    }
}
