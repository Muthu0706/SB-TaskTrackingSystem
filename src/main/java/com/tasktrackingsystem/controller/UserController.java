package com.tasktrackingsystem.controller;

import java.io.IOException;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.tasktrackingsystem.constants.TaskConstants;
import com.tasktrackingsystem.dto.UserLoginRequestDto;
import com.tasktrackingsystem.dto.UserSignupRequestDto;
import com.tasktrackingsystem.dto.UserResponseDto;
import com.tasktrackingsystem.dto.UserRequestDto;
import com.tasktrackingsystem.entity.User;
import com.tasktrackingsystem.service.UserService;
import com.tasktrackingsystem.util.UserAuthenticationTokenUtil;
import com.tasktrackingsystem.util.UserServiceImplUtil;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping(value = TaskConstants.TASK_REQUESTMAPPING)
@RequiredArgsConstructor
@Tag(name = TaskConstants.API_OPERTAION,
description = TaskConstants.API_OPERTAION)
public class UserController {

    private final UserService userService;
    private final UserAuthenticationTokenUtil jwtUtil;
    private final UserServiceImplUtil userServiceImplUtil;

    // Signup :
    @PostMapping(TaskConstants.USER_SIGNUP)
    @Operation(summary = TaskConstants.SIGNUP_NEW_USER, description = TaskConstants.SIGNUP_NEW_USER)
    @ApiResponses(value = {
        @ApiResponse(responseCode = TaskConstants.CREATED_NO_CONTENT, description = TaskConstants.USER_SIGNUP_SUCCESS_MESSAGE),
        @ApiResponse(responseCode = TaskConstants.INVALID_REQUEST_DATA_ERROR_CODE, description = TaskConstants.INVALID_REQUEST_DATA_MESSAGE)
    })
    public ResponseEntity<Void> signUp(
            @ApiParam(value = TaskConstants.SIGNUP_REQUEST_OBJECT, required = true)
            @RequestBody UserSignupRequestDto signupRequestdto) {
        userService.signUp(signupRequestdto);
        return ResponseEntity.noContent().build();
    }
        
    // Login :
    @PostMapping(TaskConstants.USER_LOGIN)
    @Operation(summary = TaskConstants.LOG_EXIST_USER, description = TaskConstants.LOG_EXIST_USER)
    @ApiResponses(value = {
        @ApiResponse(responseCode = TaskConstants.CREATED_NO_CONTENT, description = TaskConstants.USER_LOGIN_SUCCESS_MESSAGE), 
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<Map<String, Object>> login(
            @ApiParam(value = TaskConstants.LOGIN_REQUEST_OBJECT, required = true)
            @RequestBody UserLoginRequestDto loginRequestDto,
            HttpServletResponse response) {
        Map<String, Object> user = userService.login(loginRequestDto, response);
        return ResponseEntity.ok(user);
    }
        
 // Update User Details
    @PutMapping(value = TaskConstants.UPDATE_USER_DETAILS)
    @Operation(summary = TaskConstants.UPDATE_USER_DETAIL, description = TaskConstants.UPDATE_USER_DETAIL)
    @ApiResponses(value = {
        @ApiResponse(responseCode = TaskConstants.CREATED_NO_CONTENT, description = TaskConstants.USER_LOGIN_SUCCESS_MESSAGE),
        @ApiResponse(responseCode = TaskConstants.INVALID_CREDENTIALS_ERROR_CODE, description = TaskConstants.INVALID_CREDENTIALS_MESSAGE)
    })
    public ResponseEntity<Void> updateUserDetails(@RequestBody UserRequestDto userUpdateDto, HttpServletRequest request) {
        User user = userServiceImplUtil.authenticateUser(request);
        userUpdateDto.setUserid(user.getUserid());
        userService.updateUserProfile(userUpdateDto);
        return ResponseEntity.noContent().build();
    }
     
    // User GetDetails :
    @GetMapping(TaskConstants.GET_BY_IDDETAILS)
    @Operation(summary = TaskConstants.GET_USER_USERID, description = TaskConstants.GET_USER_USERID)
    @ApiResponses(value = {
        @ApiResponse(responseCode = TaskConstants.USER_RETRIEVED_SUCCESS_ERROR_CODE, description = TaskConstants.USER_RETRIEVED_SUCCESS_MESSAGE),
        @ApiResponse(responseCode = TaskConstants.USER_NOT_FOUND_ERROR, description = TaskConstants.USER_NOT_FOUND_MESSAGE)
    })
    public ResponseEntity<UserResponseDto> getUserDetail(HttpServletRequest request) { 
             String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
             String jwtToken = UserAuthenticationTokenUtil.extractJwtToken(authHeader);
             Long userId = jwtUtil.extractUserId(jwtToken);
             UserResponseDto user = userService.getUserDetails(userId);
             return ResponseEntity.ok(user);
    }
 
    // Upload User Profile :
    @PutMapping(TaskConstants.UPLOAD_PROFILE_PICTURE)
    @Operation(summary = TaskConstants.UPLOAD_USER_PROFILE, description = TaskConstants.UPLOAD_USER_PROFILE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = TaskConstants.USER_RETRIEVED_SUCCESS_ERROR_CODE, description = TaskConstants.PROFILE_PICTURE_UPLOAD_SUCCESS_MESSAGE),
        @ApiResponse(responseCode = TaskConstants.INVALID_REQUEST_DATA_ERROR_CODE, description = TaskConstants.INVALID_FILE_MESSAGE),
        @ApiResponse(responseCode = TaskConstants.USER_NOT_FOUND_ERROR, description = TaskConstants.USER_NOT_FOUND_MESSAGE)
    })
    public ResponseEntity<Void> uploadProfilePicture(
            @RequestParam(TaskConstants.FILE) MultipartFile file, 
            HttpServletRequest request) throws IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwtToken = UserAuthenticationTokenUtil.extractJwtToken(authHeader);
        Long userId = jwtUtil.extractUserId(jwtToken); 
        userService.validateUploadedFile(file);
        UserRequestDto userDto = new UserRequestDto();
        userDto.setUserid(userId);
        userDto.setProfilePicture(file.getBytes());
        userService.uploadProfilePicture(userDto);
        return ResponseEntity.ok().build();
    }
       
    // Get User Profile :
    @GetMapping(TaskConstants.GET_PROFILE_PICTURE)
    @Operation(summary = TaskConstants.GET_USER_PROFILE, description = TaskConstants.GET_USER_PROFILE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = TaskConstants.TASK_ADD_SUCCESS_ERROR_CODE, description = TaskConstants.PROFILE_PICTURE_RETRIEVED_SUCCESS_MESSAGE),
        @ApiResponse(responseCode = TaskConstants.USER_NOT_FOUND_ERROR, description = TaskConstants.USER_NOT_FOUND_MESSAGE),
        @ApiResponse(responseCode = "404", description = "Profile picture not found.")
    })
    public ResponseEntity<byte[]> getProfilePicture(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwtToken = UserAuthenticationTokenUtil.extractJwtToken(authHeader);
        Long userId = jwtUtil.extractUserId(jwtToken);        
        byte[] profilePicture = userService.getProfilePicture(userId);
        if (profilePicture == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(profilePicture);
    }

    // Logout :
    @DeleteMapping(TaskConstants.LOGOUT)
    @Operation(summary = TaskConstants.LOG_OUT_USER, description = TaskConstants.LOG_OUT_USER)
    @ApiResponses(value = {
        @ApiResponse(responseCode = TaskConstants.CREATED_NO_CONTENT, description = TaskConstants.LOGOUT_SUCCESS_MESSAGE),
        @ApiResponse(responseCode = TaskConstants.INVALID_CREDENTIALS_ERROR_CODE , description = TaskConstants.UNAUTHORIZED_ACCESS_MESSAGE)
    })
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwtToken = UserAuthenticationTokenUtil.extractJwtToken(authHeader);
        userService.blacklistToken(jwtToken);
        return ResponseEntity.noContent().build();
    }
    
}