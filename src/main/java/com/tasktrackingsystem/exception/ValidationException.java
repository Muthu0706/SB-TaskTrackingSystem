package com.tasktrackingsystem.exception;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 6401888511944850965L;
	private final String errorCode;

    public ValidationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}