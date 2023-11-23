package com.springboot.lms.ErrorAndExceptionHandling;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidUserData.class)
    public ResponseEntity<Message> invalidUserData(InvalidUserData ex){
        return this.getMessage(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<Message> userAlreadyExists(UserAlreadyExists ex){
        return this.getMessage(ex.getMessage(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnableToSaveData.class)
    public ResponseEntity<Message> unableToSaveData(UnableToSaveData ex){
        return this.getMessage(ex.getMessage(),HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(TooManyCourses.class)
    public ResponseEntity<Message> tooManyCourses(TooManyCourses ex){
        return this.getMessage(ex.getMessage(),HttpStatus.FORBIDDEN);
    }

    //gives the ResponseEntity with given code,message and time
    private ResponseEntity<Message> getMessage(String message,HttpStatusCode code){
        return ResponseEntity.status(code).body(new Message(message,new Date()));
    }
}
