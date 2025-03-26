package com.tasktrackingsystem.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
    
    private static final long serialVersionUID = 2838983047822543630L;

    private final AppErrors appErrors; 
    private final HttpStatus httpStatus; 
    
    public AppException(AppErrors appErrors, HttpStatus httpStatus) {
        super(appErrors.getErrorMessage()); 
        this.appErrors = appErrors;
        this.httpStatus = httpStatus;
    }
}