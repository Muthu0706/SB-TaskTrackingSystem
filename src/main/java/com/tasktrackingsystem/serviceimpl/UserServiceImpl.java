package com.tasktrackingsystem.serviceimpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.tasktrackingsystem.constants.TaskConstants;
import com.tasktrackingsystem.dto.UserLoginRequestDto;
import com.tasktrackingsystem.dto.UserSignupRequestDto;
import com.tasktrackingsystem.dto.UserResponseDto;
import com.tasktrackingsystem.dto.UserRequestDto;
import com.tasktrackingsystem.entity.User;
import com.tasktrackingsystem.exception.AppErrors;
import com.tasktrackingsystem.exception.AppException;
import com.tasktrackingsystem.repository.UserRepository;
import com.tasktrackingsystem.service.UserService;
import com.tasktrackingsystem.util.UserServiceImplUtil;
import com.tasktrackingsystem.mapper.TaskTrackingSystemMapper;
import com.tasktrackingsystem.util.UserAuthenticationTokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserServiceImplUtil userServiceImplUtil;
    private final PasswordEncoder passwordEncoder;
    private final TaskTrackingSystemMapper taskTrackingSystemMapper;  
    private final Set<String> blacklistedTokens = new HashSet<>();
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User signUp(UserSignupRequestDto signupRequest) {
        logger.debug(TaskConstants.USER_SIGNUP_LOG_MESSAGE, signupRequest);

        userServiceImplUtil.validateEmail(signupRequest.getEmail());
        userServiceImplUtil.checkEmailExists(signupRequest.getEmail());
        userServiceImplUtil.validatePassword(signupRequest.getPassword());

        User user = taskTrackingSystemMapper.userRequestDtoToUser(
            UserRequestDto.builder()
                    .email(signupRequest.getEmail())
                    .password(signupRequest.getPassword())
                    .build()
        );

        user.setPassword(userServiceImplUtil.encodePassword(signupRequest.getPassword()));
        user.setRole("ROLE_USER");

        User savedUser = userRepository.save(user);
        logger.info(TaskConstants.USER_SIGNUP_SUCCESS_LOG_MESSAGE, savedUser.getUserid());
        return savedUser;
    }

    @Override
    public Map<String, Object> login(UserLoginRequestDto loginRequest, HttpServletResponse response) {
        logger.debug(TaskConstants.LOGIN_LOG_MESSAGE, loginRequest);
        userServiceImplUtil.validateLoginRequestCheck(loginRequest);
        if (StringUtils.isBlank(loginRequest.getEmail()) && StringUtils.isBlank(loginRequest.getUsername())) {
            logger.error(TaskConstants.LOGIN_INVALID_CREDENTIALS_LOG_ERROR);
            throw new AppException(AppErrors.INVALID_LOGIN_CREDENTIALS, HttpStatus.UNAUTHORIZED);
        }
        User user = userServiceImplUtil.getUserByEmailOrUsername(loginRequest.getEmail(), loginRequest.getUsername());
        if (ObjectUtils.isEmpty(user) || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            logger.error(TaskConstants.LOGIN_INVALID_CREDENTIALS_LOG_ERROR);
            throw new AppException(AppErrors.INVALID_LOGIN_CREDENTIALS, HttpStatus.UNAUTHORIZED);
        }
        String token = UserAuthenticationTokenUtil.generateToken(String.valueOf(user.getUserid()), user.getEmail());
        response.setHeader(TaskConstants.AUTHORIZATION, TaskConstants.BEARER + token);
        response.setHeader(TaskConstants.ACCESS_CONTROL_EXPOSE_HEADERS, TaskConstants.AUTHORIZATION);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("errorcode", 200);
        responseMap.put("message", "Login successful");
        responseMap.put("token", token);
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            responseMap.put("username", user.getUsername());
        } else {
            responseMap.put("email", user.getEmail());
        }
        logger.info(TaskConstants.LOGIN_SUCCESS_LOG_MESSAGE, user.getUserid());
        return responseMap;
    }
    
    @Override
    public void blacklistToken(String token) {
        logger.debug(TaskConstants.TOKEN_BLACKLIST_LOG_MESSAGE, token);
        blacklistedTokens.add(token);
        logger.info(TaskConstants.TOKEN_BLACKLIST_SUCCESS_LOG_MESSAGE, token);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        logger.debug(TaskConstants.TOKEN_BLACKLIST_LOG_MESSAGE, token);
        boolean isBlacklisted = blacklistedTokens.contains(token);
        logger.info(TaskConstants.TOKEN_BLACKLIST_SUCCESS_LOG_MESSAGE, token, isBlacklisted);
        return isBlacklisted;
    }

    @Override
    public UserResponseDto getUserDetails(Long userid) {
        User user = getUserById(userid);
        return new UserResponseDto(user.getUserid(), user.getEmail(),user.getUsername(), user.getPhonenumber());
    }
    
    @Override
    public User findByUserName(String username) {
        logger.debug(TaskConstants.USER_ENTERING_FINDBYUSERNAME_LOG_MESSAGE, username);
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        logger.debug(TaskConstants.USER_ENTERING_FINDBYEMAIL_LOG_MESSAGE, email);
        return userRepository.findByEmail(email);
    }

    @Override
    public User updateUserProfile(UserRequestDto userUpdateDto) {
    	
        logger.debug(TaskConstants.USER_UPDATE_PROFILE_LOG_MESSAGE, userUpdateDto);
        User user = getUserById(userUpdateDto.getUserid());
        // Update username if provided
        if (StringUtils.isNotBlank(userUpdateDto.getUsername())) {
            user.setUsername(userUpdateDto.getUsername());
        }
        // Update phone number if provided
        if (userUpdateDto.getPhonenumber() != null) {
            userServiceImplUtil.checkPhoneNumberExists(userUpdateDto.getPhonenumber());
            userServiceImplUtil.validatePhoneNumber(userUpdateDto.getPhonenumber());
            user.setPhonenumber(userUpdateDto.getPhonenumber());
        }
        // Update password if provided
        if (StringUtils.isNotBlank(userUpdateDto.getPassword())) {
            userServiceImplUtil.validatePassword(userUpdateDto.getPassword());
            user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }
        User updatedUser = userRepository.save(user);
        logger.info(TaskConstants.USER_UPDATE_PROFILE_SUCCESS_LOG_MESSAGE, updatedUser.getUserid());
        return updatedUser;
    }
    
    
   
    @Override
    public User uploadProfilePicture(UserRequestDto userDto) {
    	
        logger.debug(TaskConstants.PROFILE_PICTURE_UPLOAD_LOG_MESSAGE, userDto);

        User user = getUserById(userDto.getUserid());
        user.setProfilePicture(userDto.getProfilePicture());

        User updatedUser = userRepository.save(user);
        logger.info(TaskConstants.PROFILE_PICTURE_UPLOAD_SUCCESS_MESSAGE, updatedUser.getUserid());
        return updatedUser;
    }

    @Override
    public void validateUploadedFile(MultipartFile file) {
    	
        logger.debug(TaskConstants.VALIDATE_UPLOADED_FILE_LOG_MESSAGE, file.getOriginalFilename());

        if (ObjectUtils.isEmpty(file)) {
            logger.error(TaskConstants.UPLOADED_FILE_NULL_ERROR_LOG);
            throw new AppException(AppErrors.FILE_SIZE_EXCEEDED, HttpStatus.BAD_REQUEST);
        }

        if (file.getSize() > TaskConstants.MAX_FILE_SIZE) {
            logger.error(TaskConstants.FILE_SIZE_EXCEEDS_ERROR_LOG, TaskConstants.MAX_FILE_SIZE);
            throw new AppException(AppErrors.FILE_SIZE_EXCEEDED, HttpStatus.BAD_REQUEST);
        }

        String contentType = file.getContentType();
        if (!Arrays.asList(TaskConstants.ALLOWED_CONTENT_TYPES).contains(contentType)) {
            logger.error(TaskConstants.INVALID_FILE_TYPE_ERROR_LOG, contentType);
            throw new AppException(AppErrors.INVALID_FILE_TYPE, HttpStatus.BAD_REQUEST);
        }

        logger.info(TaskConstants.FILE_VALIDATED_SUCCESS_LOG, file.getOriginalFilename());
    }

    @Override
    public byte[] getProfilePicture(Long userid) {
        logger.debug(TaskConstants.GET_PROFILE_PICTURE_LOG_MESSAGE, userid);
        User user = getUserById(userid);

        logger.info(TaskConstants.PROFILE_PICTURE_RETRIEVED_LOG_MESSAGE, userid);
        return user.getProfilePicture();
    }
    
    @Override
    public User getUserById(Long userid) {
        logger.debug(TaskConstants.USER_RETRIEVE_LOG_MESSAGE, userid);

        return userRepository.findById(userid)
            .orElseThrow(() -> {
                logger.error(TaskConstants.USER_NOT_FOUND_LOG_ERROR, userid);
                return new AppException(AppErrors.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
            });
    }

     
}