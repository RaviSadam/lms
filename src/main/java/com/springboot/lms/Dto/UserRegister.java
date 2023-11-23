package com.springboot.lms.Dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegister {
    @JsonAlias({"firstname","first_name"})
    private String firstName;

    @JsonAlias({"lastname","last_name"})
    private String lastName;
    private String department;
    private String email;
    private String password;

    @JsonAlias({"rollnumber","roll_number"})
    private String rollNumber;
    private List<String> roles;
    private int year;
}
