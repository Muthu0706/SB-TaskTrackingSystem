package com.tasktrackingsystem.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasktrackingsystem.constants.TaskConstants;
import com.tasktrackingsystem.dto.ErrorResponseDto;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomUnauthorizedEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, 
                         AuthenticationException authException) throws IOException {
        String token = request.getHeader(TaskConstants.AUTHORIZATION); 
        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ErrorResponseDto errorResponse = 
                    new ErrorResponseDto(TaskConstants.EMPTY_TOKEN_ERROR_CODE, TaskConstants.EMPTY_TOKEN_MESSAGE);
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
        else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            ErrorResponseDto errorResponse = 
                    new ErrorResponseDto(TaskConstants.TOKEN_EXPIRED_ERROR_CODE, TaskConstants.TOKEN_EXPIRED_MESSAGE);
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
        
    }
}
