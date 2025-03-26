package com.tasktrackingsystem.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.tasktrackingsystem.dto.UserLoginRequestDto;
import com.tasktrackingsystem.dto.UserSignupRequestDto;
import com.tasktrackingsystem.dto.UserResponseDto;
import com.tasktrackingsystem.dto.UserRequestDto;
import com.tasktrackingsystem.entity.User;
import jakarta.servlet.http.HttpServletResponse;

@Service
public interface UserService {

	User signUp(UserSignupRequestDto signupRequest);
		
	User findByUserName(String username);

	User findByEmail(String email);
	
	User getUserById(Long userid);
	
	Map<String, Object> login(UserLoginRequestDto loginRequest, HttpServletResponse response);
	
	User updateUserProfile(UserRequestDto userUpdateDto);
		        
    UserResponseDto getUserDetails(Long userid);
    
    User uploadProfilePicture(UserRequestDto userDto) ;
    
    byte[] getProfilePicture(Long userid) ;
    
    void blacklistToken(String token);
    
    boolean isTokenBlacklisted(String token);
    
    void validateUploadedFile(MultipartFile file);
    
}