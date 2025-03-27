package com.tasktrackingsystem.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Data;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class TaskConstants {
    
    // USER ENDPOINTS
    public static final String TASK_REQUESTMAPPING = "/api/auth";  
    public static final String USER_SIGNUP = "/signup";
    public static final String USER_LOGIN = "/login";
    public static final String GET_BY_IDDETAILS = "/userDetails";
    public static final String UPLOAD_PROFILE_PICTURE = "/uploadProfilePicture";
    public static final String GET_PROFILE_PICTURE = "/getProfilePicture";
    public static final String UPDATE_USER_DETAILS = "/update/user";
    public static final String LOGOUT = "/logout";
    public static final String TASK_TRACKING_SYSTEM_PRODUCTION_API = "https://sb-tasktrackingsystem-production.up.railway.app";
    
    public static final String TASK_TRACKING_SYSTEM_UI_API;
    @Value("${app.api.url}")
    public void setTaskTrackingSystemUIAPI(String uiApiUrl) {
        TASK_TRACKING_SYSTEM_UI_API = uiApiUrl;
    }

    // TASK ENDPOINTS
    public static final String ADD_TASK_DETAILS = "/add/task";
    public static final String PROGRESS_STATUS = "/{taskId}/progress";
    public static final String DELETE_BY_TASKID = "/delete/{taskId}";
    public static final String DELETE_TASK_BYUSERID = "/formattask";
    public static final String USER_GET_BY_ID = "/userByTask";
    public static final String TASK_REPORT = "/tasks/report";
    public static final String FILE = "file";
    

    // SUCCESS MESSAGES
    public static final String TASK_ADD_SUCCESS_ERROR_CODE  = "201";
    public static final String TASK_ADD_SUCCESS_MESSAGE = "Task added successfully.";
    public static final String TASK_UPDATE_SUCCESS_MESSAGE = "Task updated successfully.";
    public static final String TASK_RETRIEVED_SUCCESS_ERROR_CODE = "200";
    public static final String TASK_RETRIEVED_SUCCESS_MESSAGE = "Task retrieved successfully.";
    public static final String PROGRESS_STATUS_UPDATE_SUCCESS_MESSAGE = "Progress status updated successfully.";
    public static final String OPEN_TO_INPROGRESS_COUNT_SUCCESS_MESSAGE = "Open to in-progress task count retrieved successfully.";
    public static final String TASK_DELETE_SUCCESS_MESSAGE = "Task deleted successfully.";
    public static final String TASKS_RETRIEVED_SUCCESS_MESSAGE = "Tasks retrieved successfully.";
    public static final String TASK_DURATION_RETRIEVED_SUCCESS_MESSAGE = "Task duration retrieved successfully.";
    public static final String AVERAGE_COMPLETION_TIME_RETRIEVED_SUCCESS_MESSAGE = "Average time retrieved successfully.";
    public static final String TASK_REPORT_RETRIEVED_SUCCESS_MESSAGE = "Task report retrieved successfully.";
    public static final String USER_AND_TASKS_DELETE_SUCCESS_MESSAGE = "User and tasks deleted successfully.";
    
    // USER MESSAGES
    public static final String USER_SIGNUP_SUCCESS_MESSAGE = "User signed up successfully.";
    public static final String USER_LOGIN_SUCCESS_MESSAGE = "User logged in successfully.";
    public static final String PHONE_UPDATE_SUCCESS_MESSAGE = "Phone number updated successfully.";
    public static final String USERNAME_UPDATE_SUCCESS_MESSAGE = "Username updated successfully.";
    public static final String PASSWORD_UPDATE_SUCCESS_ERROR_CODE ="204";
    public static final String PASSWORD_UPDATE_SUCCESS_MESSAGE = "Password updated successfully.";
    public static final String USER_RETRIEVED_SUCCESS_ERROR_CODE = "200";
    public static final String USER_RETRIEVED_SUCCESS_MESSAGE = "User retrieved successfully.";
    public static final String PROFILE_PICTURE_UPLOAD_SUCCESS_MESSAGE = "Profile picture uploaded successfully.";
    public static final String PROFILE_PICTURE_RETRIEVED_SUCCESS_MESSAGE = "Profile picture retrieved successfully.";
    public static final String LOGOUT_SUCCESS_MESSAGE = "User logged out successfully.";
    
    public static final String INVALID_USER_TOKEN_FORMAT = "Invalid user ID format in the token";
    public static final String INVALID_USER_ID_TOKEN = "User ID not found in token";
    public static final String INVALID_AUTHORIZATION_HEADER  ="Invalid authorization header";
    public static final String INVALID_AUTHORIZATION_HEADER_MISSING = "Authorization header is missing or invalid";
    
    // ERROR CODES (aligned 4000 - 4050)
    public static final String UNEXPECTED_ERROR_OCCURRED_CODE = "5000";
    public static final String VALIDATION_ERROR_CODE = "4001";
    public static final String USER_NOT_FOUND_ERROR_CODE = "4002";
    public static final String USER_LOGIN_ERROR_CODE = "4003";
    public static final String FILE_SIZE_EXCEEDED_ERROR_CODE = "4004";
    public static final String INVALID_FILE_TYPE_ERROR_CODE = "4005";
    public static final String TASK_NOT_FOUND_ERROR_CODE = "4006";
    public static final String USER_EMAIL_INVALID_ERROR_CODE = "4007";
    public static final String EMAIL_ALREADY_EXISTS_ERROR_CODE = "4008";
    public static final String INVALID_PASSWORD_ERROR_CODE = "4009";
    public static final String INVALID_LOGIN_CREDENTIALS_ERROR_CODE = "4010";
    public static final String EMAIL_OR_USERNAME_REQUIRED_ERROR_CODE = "4011";
    public static final String INVALID_USERNAME_OR_EMAIL_ERROR_CODE = "4012";
    public static final String INVALID_PHONE_NUMBER_ERROR_CODE = "4013";
//    public static final String PHONE_NUMBER_TOO_SHORT_ERROR_CODE = "4014";
//    public static final String PHONE_NUMBER_NOT_NUMERIC_ERROR_CODE = "4015";
    public static final String PHONE_NUMBER_ALREADY_EXISTS_ERROR_CODE = "4016";
    public static final String PHONE_NUMBER_CANNOT_BE_NULL_ERROR_CODE = "4017";
    public static final String JWT_TOKEN_MISSING_ERROR_CODE = "4018";
    public static final String INVALID_TOKEN_ERROR_CODE = "4019";
    public static final String TOKEN_EXPIRED_ERROR_CODE = "4020";
    public static final String USER_ID_REQUIRED_ERROR_CODE = "4021";
    public static final String INVALID_USERNAME_ERROR_CODE = "4022";
    public static final String USER_PASSWORD_UPDATE_ERROR_CODE = "4023";
    public static final String INVALID_NEW_PASSWORD_ERROR_CODE = "4024";
    public static final String USER_NOT_NULL_ERROR_CODE = "4025";
    public static final String TASK_ID_NULL_OR_EMPTY_CODE = "4026";
    public static final String UNEXPECTED_ERROR_CODE = "4027";
    public static final String UNAUTHORIZED_USER_CODE = "4028";
    public static final String TASK_NOT_COMPLETED_CODE = "4029";
    public static final String TASK_COMPLETED_DURATION_CODE = "4030";
    public static final String TASK_DESCRIPTION_NOT_NULL = "4031";
    
   // ERROR MESSAGES 
    public static final String INVALID_REQUEST_DATA_ERROR_CODE = "400";
    public static final String INVALID_REQUEST_DATA_MESSAGE = "Invalid request data.";
    public static final String UNAUTHORIZED_ACCESS_MESSAGE = "Unauthorized access.";
    public static final String UNEXPECTED_ERROR_OCCURRED_MESSAGE = "An unexpected error occurred.";
    public static final String TASK_NOT_FOUND_ERROR  = "404";
    public static final String TASK_NOT_FOUND_MESSAGE = "Task not found.";
    public static final String USER_NOT_FOUND_ERROR = "404";
    public static final String USER_NOT_FOUND_MESSAGE = "User not found.";
    public static final String INVALID_CREDENTIALS_ERROR_CODE  = "401" ;
    public static final String INVALID_CREDENTIALS_MESSAGE = "Invalid User Data.";
    public static final String INVALID_FILE_MESSAGE = "Invalid File Size.";
    public static final String VALIDATION_ERROR_MESSAGE = "Validation error.";
    public static final String USER_LOGIN_ERROR_MESSAGE = "User login failed.";
    public static final String FILE_SIZE_EXCEEDED_ERROR_MESSAGE = "File size must be less than 5 MB.";
    public static final String INVALID_FILE_TYPE_ERROR_MESSAGE = "Only JPG, PNG, and JFIF file types are allowed.";
    
    // FILE CONFIGURATION
    public static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    public static final String[] ALLOWED_CONTENT_TYPES = {"image/jpeg", "image/png", "image/gif"};
    
    // JWT ERROR CODES AND MESSAGES
    public static final String JWT_TOKEN_MISSING_MESSAGE = "JWT token is missing.";
    public static final String INVALID_TOKEN_MESSAGE = "JWT token is invalid.";
    public static final String EMPTY_TOKEN_ERROR_CODE = "4020";
    public static final String EMPTY_TOKEN_MESSAGE = "Token is required and cannot be null.";
    
    // AUTHENTICATION STRINGS
    public static final String AUTHORIZATION = "Authorization";
    public static final String SECURITY_KEY = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String TOKEN_EXPIRED_MESSAGE = "Token has expired.";
    public static final String APPLICATION_JSON = "application/json";
    public static final String INVALID_TOKEN_SIGNATURE = "Invalid token signature.";
    public static final String ERROR_EXTRACTING_CLAIMS = "An error occurred while extracting claims from the token.";
    public static final String INTERNAL_SERVER_ERROR = "500";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal Server Error";
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    public static final String TOKEN_MALFORMED = "Token is malformed.";
    public static final String ALGORITHM = "HmacSHA256";
    public static final String BEARER = "Bearer ";
    
    // VALIDATION CONSTANTS
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 15;
    public static final String USER_PASSWORD_LENGTH_MESSAGE = "Password must be between 8 and 15 characters.";
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // USER VALIDATION EXCEPTION
    public static final String USER_VALIDATION_EXCEPTION = "UserValidationException: ";

    // USER PHONE NUMBER VALIDATION ERRORS 
    public static final String USERNAME_LENGTH = "15";
    
    // TASK DETAILS
    public static final String USER_FIND_EXIT = "User does not exist.";
    public static final String TASK_NOT_COMPLETED = "Task is not completed yet.";
    public static final String TASK_COMPLETED_DURATION = "Completed in %d days and %d hours.";
    public static final String UNAUTHORIZED_EDIT = "Unauthorized person Edit.";
    
    // REPORTING ERRORS
    public static final String FAILED_TO_CREATE_REPORT_DIRECTORY = "Failed to create the report directory: ";
    public static final String FAILED_TO_CREATE_DIRECTORIES_FOR_REPORT_DIRECTORY = "Failed to create directories for report directory: ";
    public static final String FAILED_TO_WRITE_REPORT_TO_FILE = "Failed to write report to ";
    
    public static final String ERROR_GENERATING_REPORT = "Error generating the report. Please try again later.";
    public static final String FAILED_RESOURCE_FILE = "Failed to create a resource for the file.";

    public static final String TASK_NOT_FOUND_ERROR_MESSAGE = "Task not found.";

    // USER VALIDATION:
    public static final String USER_PASSWORD_MANDATORY = "Password is mandatory.";
    public static final String EMAIL_REQUIRED = "Email is required";

    // PASSWORD VALIDATION:
    public static final String USER_NOT_NULL_MESSAGE = "Task request DTO or User cannot be null.";
    
    // API OPERATION:
    // User:
    public static final String API_OPERTAION = "API for managing user resources";
    
    public static final String SIGNUP_NEW_USER = "Sign Up a new user";
    public static final String LOG_EXIST_USER = "Log in an existing user";
    public static final String UPDATE_USER_PHONENUMBER = "Update user's phone number";
    public static final String UPDATE_USER_USERNAME = "Update user's username";
    public static final String UPDATE_USER_PASSWORD = "Update user's password";
    public static final String GET_USER_USERID = "Get user by ID";
    public static final String UPLOAD_USER_PROFILE = "Upload profile picture";
    public static final String UPDATE_USER_DETAIL = "Update User Detail";
    public static final String GET_USER_PROFILE = "Retrieve user's profile picture";
    public static final String LOG_OUT_USER = "Log out the user";

    // Task:
    public static final String TASK_MANAGEMENT_NAME = "Task Management";
    public static final String TASK_DESCRIPTION ="Operations related to task management";

    public static final String ADD_NEW_TASK = "Add a new task";
    public static final String UPDATE_TASK_BYID = "Update task by ID";
    public static final String USER_ID = "userid";
    public static final String GET_TASK_BYID = "Get task by ID";
    public static final String UPDATE_TASKPROGRESS_STATUS = "Update task progress status";
    public static final String DETETE_TASK_BY_ID = "Delete task by ID";
    public static final String GET_ALL_TASKS_BYUSER = "Get all tasks by user";
    public static final String GET_DURATION_OFTASK = "Get duration of task";
    public static final String GET_AVERAGE_COMPLETE_TASK = "Get average completion time for tasks";
    public static final String GET_TASK_REPORTS = "Get task reports";
    public static final String DELETE_USERBY_TASKS = "Delete user and their tasks";

    // API PARAM:
    public static final String SIGNUP_REQUEST_OBJECT = "The signup request object";
    public static final String LOGIN_REQUEST_OBJECT = "The login request object";
    public static final String USER_DETAILS_UPDATE = "User profile updated successfully.";
    
    public static final String GET_COUNT_TASK_OPEN_TO_PROGRESS = "Get count of tasks changing from open to in-progress";
    
    
    // ** Log Messages for UserServiceImpl **
    public static final String USER_SIGNUP_LOG_MESSAGE = "Entering method signUp with request: {}";
    public static final String USER_SIGNUP_SUCCESS_LOG_MESSAGE = "User signed up successfully with ID: {}";
    public static final String LOGIN_LOG_MESSAGE = "Entering method login with request: {}";
    public static final String LOGIN_INVALID_CREDENTIALS_LOG_ERROR = "Invalid username or email.";
    public static final String LOGIN_INVALID_PASSWORD_LOG_ERROR = "Invalid password for User ID: {}";
    public static final String LOGIN_SUCCESS_LOG_MESSAGE = "User ID: {} logged in successfully.";
    public static final String TOKEN_BLACKLIST_LOG_MESSAGE = "Entering method blacklistToken with token: {}";
    public static final String TOKEN_BLACKLIST_SUCCESS_LOG_MESSAGE = "Token blacklisted successfully: {}";
    public static final String PROFILE_PICTURE_UPLOAD_LOG_MESSAGE = "Entering method uploadProfilePicture with user request: {}";
    public static final String PROFILE_PICTURE_UPLOAD_SUCCESSFULLY_MESSAGE = "User ID: {} profile picture uploaded successfully.";
    public static final String USER_UPDATE_PHONE_LOG_MESSAGE = "Entering method updatePhoneNumber with user request: {}";
    public static final String USER_UPDATE_PHONE_SUCCESS_LOG_MESSAGE = "User ID: {} phone number updated successfully.";
    public static final String USER_UPDATE_USERNAME_LOG_MESSAGE = "Entering method updateUsername with user request: {}";
    public static final String USER_UPDATE_USERNAME_SUCCESS_LOG_MESSAGE = "User ID: {} username updated successfully.";
    public static final String USER_UPDATE_PASSWORD_LOG_MESSAGE = "Entering method updatePassword with user request: {}";
    public static final String USER_UPDATE_PASSWORD_SUCCESS_LOG_MESSAGE = "User ID: {} password updated successfully.";
    public static final String USER_RETRIEVE_LOG_MESSAGE = "Entering method getUserById with user ID: {}";
    public static final String USER_NOT_FOUND_LOG_ERROR = "User not found for ID: {}";
    public static final String USER_ENTERING_FINDBYUSERNAME_LOG_MESSAGE = "Entering method findByUserName with username: {}";
    public static final String USER_ENTERING_FINDBYEMAIL_LOG_MESSAGE ="Entering method findByEmail with email: {}";
    
    public static final String USER_UPDATE_PROFILE_LOG_MESSAGE = "User Details : {}";
    public static final String USER_UPDATE_PROFILE_SUCCESS_LOG_MESSAGE = "User Update Succes :{}";
 // USER LOGGING CONSTANTS
    public static final String USER_INVALID_USERNAME_LOG_ERROR = "Invalid username provided for User ID: {}";
    
 // FILE VALIDATION LOGGING CONSTANTS
    public static final String VALIDATE_UPLOADED_FILE_LOG_MESSAGE = "Entering method validateUploadedFile with file: {}";
    public static final String UPLOADED_FILE_NULL_ERROR_LOG = "Uploaded file is null.";
    public static final String FILE_SIZE_EXCEEDS_ERROR_LOG = "File size exceeds the maximum limit: {} bytes.";
    public static final String INVALID_FILE_TYPE_ERROR_LOG = "Invalid file type uploaded: {}";
    public static final String FILE_VALIDATED_SUCCESS_LOG = "Uploaded file {} validated successfully.";

    // PROFILE PICTURE LOGGING CONSTANTS
    public static final String GET_PROFILE_PICTURE_LOG_MESSAGE = "Entering method getProfilePicture with user ID: {}";
    public static final String PROFILE_PICTURE_RETRIEVED_LOG_MESSAGE = "Retrieved profile picture for User ID: {}";
    
    
    // Adding Task-related logging messages
    public static final String ENTERING_ADD_TASK = "Entering method addTask with request: {}, user: {}";
    public static final String ERROR_TASK_OR_USER_NULL = "Task request or user is null.";
    public static final String ADDING_TASK_LOG = "Adding task for user ID: {} with description: {}";
    public static final String TASK_ADDED_SUCCESS_LOG = "Task added successfully. Task ID: {}";
    
    public static final String ENTERING_EDIT_TASK = "Entering method editTask with task: {}, user: {}";
    public static final String ERROR_TASK_OR_USER_NULL_EDIT = "Task or user is null.";
    public static final String ERROR_TASK_NOT_FOUND_EDIT = "Task not found for ID: {}";
    public static final String ERROR_UNAUTHORIZED_EDIT = "User ID: {} is not authorized to edit Task ID: {}";
    public static final String TASK_UPDATED_SUCCESS_LOG = "Task ID: {} has been updated successfully.";
    
    public static final String ENTERING_GET_TASK_BY_ID = "Entering method getTaskById with task ID: {}";
    public static final String ERROR_TASK_ID_NULL = "Task ID is null.";
    public static final String TASK_RETRIEVED_LOG = "Retrieved Task ID: {}.";
    
    public static final String ENTERING_CALCULATE_DURATION = "Entering method calculateDuration for task ID: {}";
    public static final String ERROR_TASK_NOT_COMPLETED = "Task is null or not completed.";
    public static final String DURATION_CALCULATION_LOG = "Finished calculating duration for Task ID: {}: {}";
    
    public static final String ENTERING_DELETE_TASK = "Entering method deleteTask with task ID: {}";
    public static final String ERROR_TASK_NOT_FOUND_DELETE = "Task not found for ID: {}";
    
    public static final String ENTERING_UPDATE_PROGRESS_STATUS = "Entering method updateProgressStatus with task ID: {}, status: {}, user: {}";
    public static final String ERROR_TASK_ID_OR_USER_NULL = "Task ID or user is null.";
    public static final String ERROR_BOTH_TASKDESCRIPTION_AND_PROGRESSSTATUS_NULL = "Both taskDescription and progressStatus cannot be null.";
    public static final String ERROR_CANNOT_UPDATE_BOTH = "Cannot update both taskDescription and progressStatus at the same time.";
    public static final String ERROR_UNAUTHORIZED_UPDATE = "User ID: {} is not authorized to update Task ID: {}";
    public static final String TASK_PROGRESS_STATUS_UPDATED_LOG = "Updated progress status for Task ID: {}";
    
    public static final String ENTERING_COUNT_OPEN_TO_IN_PROGRESS_TASKS = "Entering method countOpenToInProgressTasks for user: {}";
    public static final String ERROR_USER_NULL = "User is null.";
    public static final String COUNT_OPEN_IN_PROGRESS_LOG = "User ID: {} has {} open or in-progress tasks.";
    
    public static final String ENTERING_DELETE_USER_AND_TASKS = "Entering method deleteUserAndTasks with user ID: {}";
    public static final String ERROR_USER_NOT_FOUND = "User not found for ID: {}";
    public static final String TASKS_DELETED_LOG = "Tasks for User ID: {} have been deleted.";
    
    public static final String ENTERING_GET_ALL_TASKS_BY_USER_ID = "Entering method getAllTasksByUserId with user ID: {}";
    public static final String TASKS_RETRIEVED_LOG = "Retrieved tasks for User ID: {}. Task count: {}";
    
    public static final String ENTERING_GET_COMPLETED_TASK_REPORT = "Entering method getCompletedTaskReport with user ID: {}";
    public static final String COMPLETED_TASK_REPORT_GENERATED_LOG = "Completed task report generated successfully for User ID: {}";
    
    public static final String ENTERING_GET_AVERAGE_COMPLETION_TIME = "Entering method getAverageCompletionTime with user ID: {}";
    public static final String NO_COMPLETED_TASKS_FOUND_LOG = "No completed tasks found for User ID: {}";
    public static final String AVERAGE_COMPLETION_TIME_LOG = "Average completion time for User ID: {} is {} minutes.";
    
    public static final String TASK_MARKED_AS_COMPLETED_LOG = "Task ID: {} marked as completed.";
    public static final String TASK_DELETED_SUCCESS_LOG = "Task ID: {} deleted successfully.";
    public static final String TASK_DURATION_LOG = "Task ID: {} Duration: {}";
    public static final String CREATED_NO_CONTENT = "204";
    public static final int LOGIN_SUCCESS_CODE = 200;
    public static final String LOGIN_SUCCESS_MESSAGE = "Login Successfully";
    public static final String BOTH_SIDE_UPDATE = "2394";
    public static final String TASK_TRACKING_SYSTEM_UI_API = "http://localhost:3000";
    public static final String API_NAME = "Task Tracking System API";
    public static final String API_VERSION = "1.0";
    public static final String API_AUTH = "bearerAuth"; 
    public static final String CROSSMAPPING = "/**";
    public static final String[] API_MAPPING = { "GET", "POST", "PUT", "DELETE", "OPTIONS" };
        
    public static String[] getApiMapping() {
    	return API_MAPPING;
    }

}
