package com.user.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UsernameNotFoundException.class)
    public ProblemDetail onException(UsernameNotFoundException exception){
        ProblemDetail problemDetail = ProblemDetail.forStatus(404);
        problemDetail.setTitle("User Not Found");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setStatus(404);
        return problemDetail;
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail onException(RuntimeException exception){
        ProblemDetail problemDetail = ProblemDetail.forStatus(403);
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setStatus(403);
        problemDetail.setTitle("User Not Authenticated");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }


    @ExceptionHandler(ApiException.class)
    public  ProblemDetail onException(ApiException exception){
        var problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Invalid Login Details");
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setProperty("timestamp",Instant.now());
        return problemDetail;
    }




}
