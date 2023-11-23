package com.springboot.lms.Controllers;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.lms.Dto.CourseDto;
import com.springboot.lms.Dto.DepartmentDto;
import com.springboot.lms.Dto.LoginRequest;
import com.springboot.lms.Dto.LoginResponse;
import com.springboot.lms.Dto.UserRegister;
import com.springboot.lms.ErrorAndExceptionHandling.Message;
import com.springboot.lms.Models.User;
import com.springboot.lms.SecurityConfiguration.JwtIssuer;
import com.springboot.lms.Services.UserService;

import lombok.RequiredArgsConstructor;


// Abc1232345

@Controller
@RequestMapping("/app")
@ResponseBody
@RequiredArgsConstructor
public class MainController {

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        System.out.println(request);
        var authentication=authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user=(User)authentication.getPrincipal();
        var token=jwtIssuer.issue(user.getId(),user.getEmail() ,user.getRolesOfUser());
        return ResponseEntity.ok().body(new LoginResponse(token, new Date()));
    } 


    @PostMapping("/register")
    public ResponseEntity<Message> register(@RequestBody UserRegister user){
        String role=userService.userRegister(user);
        return ResponseEntity.ok().body(new Message(role, new Date()));
    }
    @GetMapping("/get-avaliable-courses")
    public ResponseEntity<List<CourseDto>> getAvaliableCourses(
        @RequestParam(value = "pageNumber",required = false,defaultValue = "0")int pageNumber,
        @RequestParam(value="pageSize",required = false,defaultValue = "10") int pageSize
        ){
        return ResponseEntity.ok().body(userService.getAvaliableCourses(pageNumber,pageSize));
    }
    @GetMapping("/get-avaliable-departments")
    public ResponseEntity<List<DepartmentDto>> getAvaliableDepartment(
        @RequestParam(value = "pageNumber",required = false,defaultValue = "0")int pageNumber,
        @RequestParam(value="pageSize",required = false,defaultValue = "10") int pageSize
        ){
        return ResponseEntity.ok().body(userService.getAvaliableDepartment(pageNumber,pageSize));
    }
    @GetMapping("/get-students")
    public ResponseEntity<List<User>> getStudents(
        @RequestParam(value = "pageNumber",required = false,defaultValue = "0")int pageNumber,
        @RequestParam(value="pageSize",required = false,defaultValue = "10") int pageSize
    ){
        return ResponseEntity.ok().body(userService.getStudents(pageNumber,pageSize));
    }
    @GetMapping("/get-faculty")
    public ResponseEntity<List<User>> getFaculty(
        @RequestParam(value = "pageNumber",required = false,defaultValue = "0")int pageNumber,
        @RequestParam(value="pageSize",required = false,defaultValue = "10") int pageSize
    ){
        return ResponseEntity.ok().body(userService.getFaculty(pageNumber,pageSize));
    }

}
