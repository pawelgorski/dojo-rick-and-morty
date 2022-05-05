package com.example.dojorickandmorty.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Season")
public class SeasonNotFoundException extends RuntimeException{
    private HttpStatus status;

    public SeasonNotFoundException() {
    }

    public SeasonNotFoundException(String message) {
        super(message);
    }
    public SeasonNotFoundException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
    public SeasonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeasonNotFoundException(Throwable cause) {
        super(cause);
    }
}
