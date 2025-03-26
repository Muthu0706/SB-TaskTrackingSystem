package com.tasktrackingsystem.exception;

import java.util.HashMap;
import java.util.Map;
import com.tasktrackingsystem.constants.TaskConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppErrors {
	
    UNEXPECTED_ERROR(TaskConstants.UNEXPECTED_ERROR_CODE, TaskConstants.UNEXPECTED_ERROR_OCCURRED_MESSAGE),
    INVALID_PHONE_NUMBER(TaskConstants.INVALID_PHONE_NUMBER_ERROR_CODE, "Phone number is invalid."),
//    PHONE_NUMBER_TOO_SHORT(TaskConstants.PHONE_NUMBER_TOO_SHORT_ERROR_CODE, "Phone number is too short. It must be at least 10 characters long."),
//    PHONE_NUMBER_NOT_NUMERIC(TaskConstants.PHONE_NUMBER_NOT_NUMERIC_ERROR_CODE, "Phone number should only contain numbers."),
    PHONE_NUMBER_ALREADY_EXISTS(TaskConstants.PHONE_NUMBER_ALREADY_EXISTS_ERROR_CODE, "Phone number already exists."),
    PHONE_NUMBER_CANNOT_BE_NULL(TaskConstants.PHONE_NUMBER_CANNOT_BE_NULL_ERROR_CODE, "Phone number cannot be null."),
    USER_NOT_FOUND(TaskConstants.USER_NOT_FOUND_ERROR_CODE, "User not found."),
    USER_LOGIN_FAILED(TaskConstants.USER_LOGIN_ERROR_CODE, "User login failed."),
    FILE_SIZE_EXCEEDED(TaskConstants.FILE_SIZE_EXCEEDED_ERROR_CODE, "File size exceeded."),
    INVALID_FILE_TYPE(TaskConstants.INVALID_FILE_TYPE_ERROR_CODE, "Invalid file type."),
    TASK_NOT_FOUND(TaskConstants.TASK_NOT_FOUND_ERROR_CODE, "Task not found."),
    INVALID_EMAIL(TaskConstants.USER_EMAIL_INVALID_ERROR_CODE, "Invalid email address."),
    EMAIL_ALREADY_EXISTS(TaskConstants.EMAIL_ALREADY_EXISTS_ERROR_CODE, "Email address already exists."),
    INVALID_PASSWORD(TaskConstants.INVALID_PASSWORD_ERROR_CODE, "Password must be between 8 and 15 characters."),
    INVALID_LOGIN_CREDENTIALS(TaskConstants.INVALID_LOGIN_CREDENTIALS_ERROR_CODE, "Invalid Login Credential."),
    EMAIL_OR_USERNAME_REQUIRED(TaskConstants.EMAIL_OR_USERNAME_REQUIRED_ERROR_CODE, "Email or Username is required."),
    INVALID_USERNAME_OR_EMAIL(TaskConstants.INVALID_USERNAME_OR_EMAIL_ERROR_CODE, "Invalid Username or Email."),
    JWT_TOKEN_MISSING(TaskConstants.JWT_TOKEN_MISSING_ERROR_CODE, "JWT token is missing."),
    INVALID_TOKEN(TaskConstants.INVALID_TOKEN_ERROR_CODE, "JWT token is invalid."),
    TOKEN_EXPIRED(TaskConstants.TOKEN_EXPIRED_ERROR_CODE, "Token has expired."),
    USER_PASSWORD_UPDATE_FAILED(TaskConstants.USER_PASSWORD_UPDATE_ERROR_CODE, "User password update failed."),
    USER_NOT_NULL(TaskConstants.USER_NOT_NULL_ERROR_CODE, "User cannot be null."),
    TASK_ID_NULL_OR_EMPTY(TaskConstants.TASK_ID_NULL_OR_EMPTY_CODE, "Task ID cannot be null or empty."),
    UNAUTHORIZED_EDIT(TaskConstants.UNAUTHORIZED_USER_CODE, "Unauthorized User"),
    TASK_NOT_COMPLETED(TaskConstants.TASK_NOT_COMPLETED_CODE, "Task not complete"),
    TASK_COMPLETED_DURATION(TaskConstants.TASK_COMPLETED_DURATION_CODE, "Task Completed Duration"),
    INVALID_UPDATE_COMBINATION(TaskConstants.BOTH_SIDE_UPDATE, "Task Data Update"),
    TASK_DESCRIPTION_NOT_NULL(TaskConstants.TASK_DESCRIPTION_NOT_NULL,"Task Description is null"),
	CREATED_NO_CONTENT(TaskConstants.CREATED_NO_CONTENT,"User profile updated successfully");
    
    private String errorCode;
    private String errorMessage;
    
    private static final Map<String, AppErrors> appErrorsMap = new HashMap<>();
    
    static {
        for (AppErrors error : AppErrors.values()) {
            appErrorsMap.put(error.getErrorCode(), error);
        }
    }

    public static AppErrors getAppErrorByErrorCode(String errorCode) {
        return AppErrors.appErrorsMap.get(errorCode);
    }
}