package com.springboot.lms.Services;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.lms.Dto.QuizPost;
import com.springboot.lms.Dto.StudentScoreProjection;
import com.springboot.lms.Dto.StudentScoresDto;
import com.springboot.lms.ErrorAndExceptionHandling.InvalidUserData;
import com.springboot.lms.Models.Course;
import com.springboot.lms.Models.Question;
import com.springboot.lms.Models.Quiz;
import com.springboot.lms.Models.User;
import com.springboot.lms.Repositories.CourseRepo;
import com.springboot.lms.Repositories.QuestionRepo;
import com.springboot.lms.Repositories.QuizRepo;
import com.springboot.lms.Repositories.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacultyService {
    private final UserRepo userRepo;
    private final QuizRepo quizRepo;
    private final QuestionRepo questionRepo;
    private final CourseRepo courseRepo;
    private final PasswordEncoder passwordEncoder;

    public void addQuiz(QuizPost quizPost){
        
        String message=this.checkValidity(quizPost);
        if(!message.equals(""))
            throw new InvalidUserData(message);
        Quiz quiz=new Quiz();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date startDateSql=null,endDateSql=null;
        try{
            java.util.Date startDate = dateFormat.parse(quizPost.getStartDate());
            startDateSql = new java.sql.Date(startDate.getTime());

            java.util.Date endDate=dateFormat.parse(quizPost.getEndDate());
            endDateSql=new Date(endDate.getTime());
            if(startDate.getTime()>endDate.getTime() || startDate.getTime()<(new java.util.Date()).getTime())
                throw new InvalidUserData("Invalid Date");

        }
        catch(Exception ex){
            throw new InvalidUserData("Invalid date is given");
        }
        Course course=courseRepo.findByCourseCode(quizPost.getCourseCode());
        quiz.setCourse(course);
        quiz.setEndDate(endDateSql);
        quiz.setQuestions(quizPost.getQuestions());
        quiz.setQuizName(quizPost.getName());
        quiz.setStartDate(startDateSql);
        for(Question question:quiz.getQuestions()){
            question.setQuizes(quiz);
            questionRepo.save(question);
        }
        quizRepo.save(quiz);
    }

    public List<StudentScoresDto> getUserAttemptedQuiz(long id,int pageNumber,int pageSize){
        Pageable page=PageRequest.of(pageNumber, pageSize);
        List<StudentScoresDto> result=new ArrayList<>();
        for(StudentScoreProjection scoreProjection:quizRepo.getUserAttemptedQuiz(id,page)){
            StudentScoresDto studentScoresDto=new StudentScoresDto();
            studentScoresDto.setFirstName(scoreProjection.getFirstName());
            studentScoresDto.setLastName(scoreProjection.getLastName());
            studentScoresDto.setRollNumber(scoreProjection.getRollNumber());
            studentScoresDto.setId(scoreProjection.getId());
            result.add(studentScoresDto);
        }
        return result;
    }

    public void updatePassword(long id,String password){
        if(password==null || password.length()<8)
            throw new InvalidUserData("Password length is too short");
        User user=userRepo.findById(id);
        if(user==null || user.getPassword().equals(password))
            throw new InvalidUserData("User Not found or Invalid password");
        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);
        return;
    }


    private String checkValidity(QuizPost quizPost) {
        StringBuilder message=new StringBuilder();
        if(quizPost.getName()==null || quizPost.getName().equals(""))
            message.append("Name field is missing ");
        if(quizPost.getCourseCode()==null)
            message.append("course code is missing ");
        if(quizPost.getQuestions().isEmpty() || quizPost.getQuestions().size()>20)
            message.append("Questions are missing");
        for(Question question:quizPost.getQuestions()){
            if(question.getCorrectOption()==null || question.getCorrectOption().equals("")){
                message.append("Invalid Question data ");
                break;
            }
            if(question.getOptions().isEmpty() || question.getOptions().size()<2 || question.getOptions().size()>4){
                message.append("Invalid questions data");
                break;
            }
            if(question.getCorrectOption()==null || question.getQuestion().contains(question.getCorrectOption())){
                message.append("Invalid correct options ");
                break;
            }
            if(question.getQuestionName()==null || question.getQuestionName().equals("")){
                message.append("Question name is missing");
                break;
            }
            if(question.getQuestion()==null){
                message.append("Question description missing ");
                break;
            }
        }
        return message.toString();
    }
}
