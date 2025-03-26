package com.tasktrackingsystem.controller;

import com.tasktrackingsystem.dto.UserSignupRequestDto;
import com.tasktrackingsystem.dto.UserRequestDto;
import com.tasktrackingsystem.entity.User;
import com.tasktrackingsystem.service.UserService;
import com.tasktrackingsystem.testutils.UserTestUtils;
import com.tasktrackingsystem.util.UserAuthenticationTokenUtil;
import com.tasktrackingsystem.util.UserServiceImplUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private UserAuthenticationTokenUtil jwtUtil;

    @Mock
    private UserServiceImplUtil userServiceImplUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private MultipartFile file;

    @Test
    void testsignUp() {
    	
        UserSignupRequestDto signupRequestDto = UserTestUtils.saveSignUp();
        Mockito.when(userService.signUp(signupRequestDto)).then(CALLS_REAL_METHODS);
        ResponseEntity<Void> response = userController.signUp(signupRequestDto);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService).signUp(signupRequestDto);
    }

//    @Test
//     void testLogin() {
//        LoginRequestDto loginRequestDto = new LoginRequestDto(); 
//        ResponseEntity<Void> result = userController.login(loginRequestDto, response);
//        verify(userService).login(loginRequestDto, response);
//        assertEquals(ResponseEntity.noContent().build(), result);
//    }


    @Test
    void testuploadProfilePicture() throws IOException {
        User mockUser = UserTestUtils.MockUser();
        UserRequestDto mockUserRequestDto = UserTestUtils.MockUserRequestDto();
        when(userServiceImplUtil.authenticateUser(request)).thenReturn(mockUser);
        when(file.getBytes()).thenReturn(new byte[0]);
        ResponseEntity<Void> response = userController.uploadProfilePicture(file, request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).uploadProfilePicture(mockUserRequestDto);
    }

    @Test
    void testgetProfilePicture() {
    	
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer token");
        when(jwtUtil.extractUserId("token")).thenReturn(1L);
        when(userService.getProfilePicture(1L)).thenReturn(new byte[0]);
        ResponseEntity<byte[]> response = userController.getProfilePicture(request);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testLogout() {
    	
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer token");
        ResponseEntity<Void> response = userController.logout(request);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService).blacklistToken("token");
    }
}