package com.example.dojorickandmorty.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@EnableWebMvc
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {
            SeasonNotFoundException.class
            })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    private ErrorMessage handleSeasonNotFoundException(Exception exception) {
        return buildErrorMessage(HttpStatus.NOT_FOUND, exception);
    }

    private ErrorMessage buildErrorMessage(HttpStatus status, Exception exception) {
        return new ErrorMessage(status, exception);
    }
}




