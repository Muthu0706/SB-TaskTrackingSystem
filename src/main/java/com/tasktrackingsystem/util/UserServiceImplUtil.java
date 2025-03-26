package com.tasktrackingsystem.util;

import java.util.regex.Pattern;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.tasktrackingsystem.constants.TaskConstants;
import com.tasktrackingsystem.dto.UserLoginRequestDto;
import com.tasktrackingsystem.entity.User;
import com.tasktrackingsystem.exception.AppException;
import com.tasktrackingsystem.exception.AppErrors;
import com.tasktrackingsystem.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserServiceImplUtil {

    private final UserRepository userRepository; 
    private final PasswordEncoder passwordEncoder;
    private final UserAuthenticationTokenUtil jwtUtil;

    
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
    
    public void validateEmail(String email) throws AppException {
    	
        if (!isValidEmail(email)) {
            throw new AppException(AppErrors.INVALID_EMAIL, HttpStatus.BAD_REQUEST);
        }
    }
    
    public void checkEmailExists(String email) throws AppException {
    	
        if (userRepository.findByEmail(email) != null) {
            throw new AppException(AppErrors.EMAIL_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
    }
    
    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(TaskConstants.EMAIL_REGEX);
        return email != null && pattern.matcher(email).matches();
    }
    
    public void validateLoginRequestCheck(UserLoginRequestDto loginRequest) {
    	
        if (StringUtils.isBlank(loginRequest.getEmail()) && StringUtils.isBlank(loginRequest.getUsername())) {
            throw new AppException(AppErrors.INVALID_LOGIN_CREDENTIALS, HttpStatus.BAD_REQUEST);
        }
    }

    public void validateUserAndPassword(String password, User user) {
        if (ObjectUtils.isEmpty(user) || !passwordEncoder.matches(password, user.getPassword())) {
            throw new AppException(ObjectUtils.isEmpty(user) ? AppErrors.INVALID_LOGIN_CREDENTIALS : AppErrors.INVALID_LOGIN_CREDENTIALS, HttpStatus.BAD_REQUEST);
        }
    }

    public User getUserByEmailOrUsername(String email, String username) {
    	
        if (email != null && !email.isEmpty()) {
            return userRepository.findByEmail(email);
        } else if (username != null && !username.isEmpty()) {
            return userRepository.findByUsername(username);
        }
        return null;
    }

    public boolean isValidPassword(String password) {
    	
        return password.length() >= TaskConstants.MIN_PASSWORD_LENGTH && 
               password.length() <= TaskConstants.MAX_PASSWORD_LENGTH &&
               password.matches(".*[!@#$%^&*(),.?\":{}|<>].*") &&
               password.matches(".*\\d.*") &&
               password.matches(".*[A-Z].*");
    }
                
    public void validatePhoneNumber(Long phonenumber) {
    	
    	if (phonenumber == null) {
            throw new AppException(AppErrors.PHONE_NUMBER_CANNOT_BE_NULL, HttpStatus.BAD_REQUEST);
        }

        if (!isValidPhonenumber(phonenumber)) {
            throw new AppException(AppErrors.INVALID_PHONE_NUMBER, HttpStatus.BAD_REQUEST);
        }
    }
     
    public boolean isValidPhonenumber(Long phonenumber) {
    	
        if (phonenumber == null) return false;
        String phonenumberStr = String.valueOf(phonenumber);
        return phonenumberStr.matches("\\d{10}");
    }
       
    public void checkPhoneNumberExists(Long phonenumber) throws AppException {
    	
        if (userRepository.findByPhonenumber(phonenumber) != null) {
            throw new AppException(AppErrors.PHONE_NUMBER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
    }   
    
    public User authenticateUser(HttpServletRequest request) {
    	
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwtToken = UserAuthenticationTokenUtil.extractJwtToken(authHeader);
        String email = jwtUtil.extractUserEmail(jwtToken);
        User user = userRepository.findByEmail(email);
        return (StringUtils.isNotBlank(jwtToken) && user != null && jwtUtil.validateToken(jwtToken)) ? user : null;
    }
         
    public void setTokenHeaders(HttpServletResponse response, String token) {
    	
        response.setHeader(TaskConstants.AUTHORIZATION, TaskConstants.BEARER + token);
        response.setHeader(TaskConstants.ACCESS_CONTROL_EXPOSE_HEADERS, TaskConstants.AUTHORIZATION);
    }
    
    public void validatePassword(String password) {
    	
        if (ObjectUtils.isEmpty(password) || password.length() < TaskConstants.MIN_PASSWORD_LENGTH || password.length() > TaskConstants.MAX_PASSWORD_LENGTH) {
            throw new AppException(AppErrors.INVALID_PASSWORD, HttpStatus.BAD_REQUEST);
        }
    }
}