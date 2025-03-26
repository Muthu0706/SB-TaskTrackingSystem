package com.tasktrackingsystem.exception;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import com.tasktrackingsystem.constants.TaskConstants;
import com.tasktrackingsystem.dto.ErrorResponseDto;

@ControllerAdvice
public class AppExceptionHandler {

    	@ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception exception) {
	        ErrorResponseDto errorResponse = new ErrorResponseDto(TaskConstants.INTERNAL_SERVER_ERROR, TaskConstants.UNEXPECTED_ERROR_OCCURRED_MESSAGE);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse); 
	    }
        
	    @ExceptionHandler(AppException.class)
	    public ResponseEntity<Map<String, String>> handleAppException(AppException exception) {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("errorCode", exception.getAppErrors().getErrorCode());
	        errorResponse.put("errorMessage", exception.getAppErrors().getErrorMessage());
	        return ResponseEntity.status(exception.getHttpStatus()).body(errorResponse);
	    }
	   	   	   
	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getFieldErrors().forEach(error -> 
	            errors.put(error.getField(), error.getDefaultMessage())
	        );
	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	    }    
	    
	    @ExceptionHandler(MaxUploadSizeExceededException.class)
	    public ResponseEntity<Map<String, String>> handleMaxSizeException(MaxUploadSizeExceededException exc) {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("errorCode", TaskConstants.FILE_SIZE_EXCEEDED_ERROR_CODE);
	        errorResponse.put("errorMessage", TaskConstants.FILE_SIZE_EXCEEDED_ERROR_MESSAGE);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    }
	    	   
	    @ExceptionHandler(NullPointerException.class)
	    public ResponseEntity<ErrorResponseDto> handleNullPointerException(NullPointerException exception) {
	        String errorMessage = exception.getMessage() != null ? exception.getMessage() : "A required field was null.";
	        ErrorResponseDto errorResponse = new ErrorResponseDto(TaskConstants.INTERNAL_SERVER_ERROR, errorMessage);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	    }

	    @ExceptionHandler(ValidationException.class) 
	    public ResponseEntity<ErrorResponseDto> handleValidationException(ValidationException exception) {
	        ErrorResponseDto errorResponse = new ErrorResponseDto(exception.getErrorCode(), exception.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	    }
	    
	    @ExceptionHandler(HttpMessageNotReadableException.class)
	    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
	    	ErrorResponseDto errorResponse = new ErrorResponseDto("Invalid Data or missing Data", "4014");
	        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	    }
	    
	    private static final Logger logger = LoggerFactory.getLogger(AppExceptionHandler.class);

	    @AfterThrowing(pointcut = "execution(* com.tasktrackingsystem.controller.*.*(..))", throwing = "ex")
	    public void logAfterThrowing(Exception ex) {
	        logger.error("Exception caught in UserController: ", ex);
	    }
	    
}

