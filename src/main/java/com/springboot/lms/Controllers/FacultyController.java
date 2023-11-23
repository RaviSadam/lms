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

import com.springboot.lms.Dto.QuizPost;
import com.springboot.lms.Dto.StudentScoresDto;
import com.springboot.lms.ErrorAndExceptionHandling.Message;
import com.springboot.lms.Services.FacultyService;

import lombok.RequiredArgsConstructor;

@Controller
@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    @PostMapping("/post-quiz")
    public ResponseEntity<Message> postQuiz(@RequestBody QuizPost quiz){
        facultyService.addQuiz(quiz);
        return ResponseEntity.ok().body(new Message("Quiz was added", new Date()));
    }

    @GetMapping("/get-students-attempted/{quiz-id}")
    public ResponseEntity<List<StudentScoresDto>> getUsersAttemptedQuiz(
        @PathVariable("quiz-id") long id,
        @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNuber,
        @RequestParam(value="pageSize",defaultValue = "10",required = false)int pageSize){
        return ResponseEntity.ok().body(facultyService.getUserAttemptedQuiz(id,pageNuber,pageSize));
    }
    @PostMapping("/update-password/{id}")
    public ResponseEntity<Message> updatePassword(@PathVariable("id") long id,@RequestParam("password") String password){
        facultyService.updatePassword(id, password);
        return ResponseEntity.ok().body(new Message("Password updated", new Date()));
    }
}
