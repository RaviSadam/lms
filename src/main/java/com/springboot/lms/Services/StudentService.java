package com.springboot.lms.Services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.lms.Dto.CourseRegister;
import com.springboot.lms.Dto.QuestionDto;
import com.springboot.lms.Dto.QuizValidationDto;
import com.springboot.lms.Dto.QuizValidationRequest;
import com.springboot.lms.ErrorAndExceptionHandling.InvalidUserData;
import com.springboot.lms.ErrorAndExceptionHandling.TooManyCourses;
import com.springboot.lms.ErrorAndExceptionHandling.UnableToSaveData;
import com.springboot.lms.Models.Course;
import com.springboot.lms.Models.Question;
import com.springboot.lms.Models.Quiz;
import com.springboot.lms.Models.User;
import com.springboot.lms.Repositories.CourseRepo;
import com.springboot.lms.Repositories.QuestionRepo;
import com.springboot.lms.Repositories.QuizRepo;
import com.springboot.lms.Repositories.UserRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final UserRepo userRepo;
    private final CourseRepo courseRepo; 
    private final PasswordEncoder passwordEncoder;
    private final QuizRepo quizRepo;
    private final QuestionRepo questionRepo;

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

    //student registring for courses
    @Transactional
    public int courseRegister(long userId,CourseRegister courseIds){
        if(courseIds.getCourseIds().isEmpty())
            throw new TooManyCourses("No Course is specified");
        if(courseIds.getCourseIds().size()>5)
            throw new TooManyCourses("Can't register for more than 5 courses");
        try{
            User user=userRepo.findById(userId);
            List<Course> userCourses=courseRepo.findAllCoursesByUserId(userId);
            List<Course> courses=courseRepo.findAllCoursesByIds(courseIds.getCourseIds());
            if(userCourses.size()+courses.size()>5)
                throw new TooManyCourses("Can't register for more than 5 courses registred courses:"+userCourses.size()+"Current registratoin courses:"+courses.size());
            System.out.println(courses.size());
            user.setCourses(userCourses);
            for(Course course:courses){
                if(course.getStartDate().getTime() <(new Date()).getTime()){
                    courses.remove(course);
                    continue;
                }
                if(course.getUsers()==null){
                    course.setUsers(List.of(user));
                }
                else{
                    course.getUsers().add(user);
                }
                courseRepo.save(course);
            }
            if(user.getCourses().isEmpty())
                user.setCourses(courses);
            else    
                user.getCourses().addAll(courses);  
            System.out.println(user.getCourses().size());  
            userRepo.save(user);
            return 0;
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw new UnableToSaveData("Unable to save the data due to exception in server");
        }   
    } 


    public QuizValidationDto validateQuiz(List<QuizValidationRequest> quizValidationRequest){
        Map<Long,String> options=new HashMap<>();
        for(QuizValidationRequest quizValidationRequest2:quizValidationRequest){
            options.put(quizValidationRequest2.getId(),quizValidationRequest2.getSelectedOption());
        }
        List<QuestionDto> answer=new ArrayList<>();
        int score=0;
        for(Question question:questionRepo.getQuestionCorrectOption(options.keySet())){
            QuestionDto dto=new QuestionDto();
            dto.setCorrectOption(question.getCorrectOption());
            dto.setId(question.getId());
            dto.setCorrectOption(question.getCorrectOption());
            dto.setOptions(new HashSet<>(question.getOptions()));
            if(options.get(question.getId()).equals(question.getCorrectOption()))
                score+=10;
            answer.add(dto);
        }
        QuizValidationDto ans=new QuizValidationDto();
        ans.setDetails(answer);
        ans.setScore(score);
        return ans;
    }

    public Quiz takeQuiz(long quizId){
        return quizRepo.findById(quizId);
    }

}
