package com.tasktrackingsystem.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.tasktrackingsystem.constants.TaskConstants;
import com.tasktrackingsystem.dto.UserSignupRequestDto;
import com.tasktrackingsystem.dto.UserRequestDto;
import com.tasktrackingsystem.entity.User;
import com.tasktrackingsystem.mapper.TaskTrackingSystemMapper;
import com.tasktrackingsystem.repository.UserRepository;
import com.tasktrackingsystem.testconstant.TaskTestConstants;
import com.tasktrackingsystem.testutils.UserTestUtils;
import com.tasktrackingsystem.util.UserAuthenticationTokenUtil;
import com.tasktrackingsystem.util.UserServiceImplUtil;
import com.tasktrackingsystem.dto.UserLoginRequestDto;
import jakarta.servlet.http.HttpServletResponse;

@ExtendWith(SpringExtension.class)
public class UserServiceImplTest {
	
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
    @Mock
    private UserServiceImplUtil userServiceImplUtil;

	@Mock
	private TaskTrackingSystemMapper taskTrackingSystemMapper;
	
	@Mock
	private  PasswordEncoder passwordEncoder;
	
    @Mock
    private HttpServletResponse response;
    
	@Mock
	private UserRepository userRepository;
			
	@Test
	void testSignUp() {
		
	    UserSignupRequestDto signupRequest = UserTestUtils.saveSignUp();
	    User expectedUser = UserTestUtils.MockExcepctedUser();
	    Mockito.when(taskTrackingSystemMapper.userRequestDtoToUser(any(UserRequestDto.class))).thenReturn(expectedUser);
	    Mockito.when(userServiceImplUtil.encodePassword(anyString())).thenReturn(TaskTestConstants.MOCK_ENCODE_PASSWORD);
	    Mockito.when(userRepository.save(any(User.class))).thenReturn(expectedUser); 
	    userServiceImpl.signUp(signupRequest);
	    verify(userRepository, times(1)).save(expectedUser); 
	}

	@Test
	void testfindByUserName() {
	    User mockUser = UserTestUtils.MockUser();
		String mockuserName = TaskTestConstants.MOCK_USERNAME;
		Mockito.when(userRepository.findByUsername(mockuserName)).thenReturn(mockUser);
		assertEquals(mockUser, userServiceImpl.findByUserName(mockuserName));
	}
	
	@Test
	void testfindByEmail() {
		
		User mockUser = UserTestUtils.MockUser();
		String mockEmail = TaskTestConstants.MOCK_EMAIL;
		Mockito.when(userRepository.findByEmail(mockEmail)).thenReturn(mockUser);
		assertEquals(mockUser, userServiceImpl.findByEmail(mockEmail));
	}
					
	@Test
	public void testLogin() {
		
	    UserLoginRequestDto loginRequestDto = UserTestUtils.MockLoginRequestDto();
	    User mockUser = UserTestUtils.MockUser();
	    doNothing().when(userServiceImplUtil).validateLoginRequestCheck(loginRequestDto);
	    Mockito.when(userServiceImplUtil.getUserByEmailOrUsername(loginRequestDto.getEmail(), null))
	        .thenReturn(mockUser);
	    Mockito.when(passwordEncoder.matches(loginRequestDto.getPassword(), mockUser.getPassword()))
	        .thenReturn(true); 
	    try (MockedStatic<UserAuthenticationTokenUtil> mocked = mockStatic(UserAuthenticationTokenUtil.class)) {
	        String expectedToken = TaskTestConstants.MOCK_TOKEN_GET;
	        mocked.when(() -> UserAuthenticationTokenUtil.generateToken(String.valueOf(mockUser.getUserid()), mockUser.getEmail()))
	              .thenReturn(expectedToken);
	        HttpServletResponse mockResponse = mock(HttpServletResponse.class);
	        doNothing().when(mockResponse).setHeader(anyString(), anyString());
	        Map<String, Object> responseMap = userServiceImpl.login(loginRequestDto, mockResponse);
	        assertNotNull(responseMap);
	        assertEquals(expectedToken, responseMap.get(TaskTestConstants.MOCK_TOKEN_GET));
	        assertEquals(mockUser.getUserid(), responseMap.get(TaskTestConstants.MOCK_USERID));
	        assertEquals(mockUser.getEmail(), responseMap.get(TaskTestConstants.MOCK_EMAIL_GET));
	        verify(mockResponse).setHeader(TaskConstants.AUTHORIZATION, TaskConstants.BEARER + expectedToken);
	        verify(mockResponse).setHeader(TaskConstants.ACCESS_CONTROL_EXPOSE_HEADERS, TaskConstants.AUTHORIZATION);
	    }
	}
		
	@Test
	void testUploadProfilePicture() {
		
	    User mockUser = UserTestUtils.MockUser();
	    UserRequestDto mockUserRequestDto = UserTestUtils.MockUserRequestDto();
	    Mockito.when(userRepository.findById(mockUser.getUserid())).thenReturn(Optional.of(mockUser));
        Mockito.when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
	        User userToSave = invocation.getArgument(0);
	        userToSave.setProfilePicture(mockUserRequestDto.getProfilePicture());
	        return userToSave;
	    });
	    User uploadUserProfile = userServiceImpl.uploadProfilePicture(mockUserRequestDto);    
	    assertEquals(mockUser.getProfilePicture(), uploadUserProfile.getProfilePicture());
	    verify(userRepository, times(1)).save(mockUser); 
	}
	
	@Test
	void testGetProfilePicture() {
		
	    User mockUser = UserTestUtils.MockUser();
        Long mockUserId = 1L;
	    Mockito.when(userRepository.findById(mockUser.getUserid())).thenReturn(Optional.of(mockUser));
		assertEquals(mockUser.getProfilePicture(), userServiceImpl.getProfilePicture(mockUserId));
	}

}
