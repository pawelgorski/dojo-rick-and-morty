package com.example.dojorickandmorty.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ErrorMessage {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timeStamp;
    private int status;
    private String error;
    private String message;

    public ErrorMessage(HttpStatus status, Exception exception) {
        this.timeStamp = LocalDateTime.now();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = exception.getMessage();
    }
}
