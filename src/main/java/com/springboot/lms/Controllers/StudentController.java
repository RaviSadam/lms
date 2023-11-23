package com.springboot.lms.Controllers;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.lms.Dto.CourseRegister;
import com.springboot.lms.Dto.QuizValidationDto;
import com.springboot.lms.Dto.QuizValidationRequest;
import com.springboot.lms.ErrorAndExceptionHandling.Message;
import com.springboot.lms.Models.Quiz;
import com.springboot.lms.Services.StudentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/student")
@ResponseBody
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/course-register/{id}")
    public ResponseEntity<Message> courseRegister(@PathVariable("id") long userId,@RequestBody CourseRegister courseRegister){
        studentService.courseRegister(userId,courseRegister);
        return ResponseEntity.ok().body(new Message("Your are registration is successfully ",new Date()));
    } 
    @PostMapping("/update-password/{id}")
    public ResponseEntity<Message> updatePassword(@PathVariable("id") long id,@RequestParam("password") String password){
        studentService.updatePassword(id, password);
        return ResponseEntity.ok().body(new Message("Password updated", new Date()));
    }

    @GetMapping("/take-quiz/{quizId}")
    public ResponseEntity<Quiz> takeQuiz(@PathVariable("quizId")long quizId){
        return ResponseEntity.ok().body(studentService.takeQuiz(quizId));
    }

    @PostMapping("/validate-quiz")
    public ResponseEntity<QuizValidationDto> validateQuiz(@RequestBody List<QuizValidationRequest> quizValidationRequest){
        return ResponseEntity.ok().body(studentService.validateQuiz(quizValidationRequest));
    }

    

}
