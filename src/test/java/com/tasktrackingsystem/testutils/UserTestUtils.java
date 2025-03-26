package com.tasktrackingsystem.testutils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

import com.tasktrackingsystem.dto.UserLoginRequestDto;
import com.tasktrackingsystem.dto.UserSignupRequestDto;
import com.tasktrackingsystem.dto.UserRequestDto;
import com.tasktrackingsystem.entity.User;
import com.tasktrackingsystem.testconstant.TaskTestConstants;

public class UserTestUtils {
	
	public static UserSignupRequestDto  saveSignUp() {
		UserSignupRequestDto signupRequest = new UserSignupRequestDto();
		signupRequest.setEmail(TaskTestConstants.MOCK_EMAIL);
		signupRequest.setPassword(TaskTestConstants.MOCK_PASSWORD);
		return signupRequest;
	}
	
	public static User MockUser() {
		User user = new User();
        user.setUserid(1L);
		user.setUsername(TaskTestConstants.MOCK_USERNAME);

		user.setEmail(TaskTestConstants.MOCK_EMAIL);
		user.setPassword(TaskTestConstants.MOCK_PASSWORD);
		user.setPhonenumber(1234567890L);
	    byte[] mockProfilePicture = "mockPicture".getBytes(StandardCharsets.UTF_8);
	    user.setProfilePicture(mockProfilePicture); 
		user.setRole(TaskTestConstants.MOCK_ROLE_TYPE);
		return user;
	}
	
	
	public static User MockExcepctedUser() {
		User expectedUser = new User();
	    expectedUser.setPassword("encodedPassword"); 
	    expectedUser.setRole("ROLE_USER");
		return expectedUser;
		
	}
	
	public static UserLoginRequestDto MockLoginRequestDto() {
	    UserLoginRequestDto loginRequestDto = new UserLoginRequestDto(); 
	    loginRequestDto.setEmail(TaskTestConstants.MOCK_EMAIL);
	    loginRequestDto.setPassword(TaskTestConstants.MOCK_PASSWORD1); 
		return loginRequestDto;
		
	}

	public static UserRequestDto MockUserRequestDto() {
		UserRequestDto userRequestDto = new UserRequestDto();
	    userRequestDto.setUserid(1L);
	    userRequestDto.setPhonenumber(9876543210L);
	    userRequestDto.setUsername("Muthu");
	    userRequestDto.setPassword(TaskTestConstants.MOCK_PASSWORD1);
		return userRequestDto;
	}
	
    public static Optional<User> getMockUserOptional() {
        return Optional.ofNullable(MockUser());
    }   
}
