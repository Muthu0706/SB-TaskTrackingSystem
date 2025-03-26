package com.tasktrackingsystem.filter;

import java.io.IOException;
import java.io.PrintWriter;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tasktrackingsystem.config.SecurityConfig;
import com.tasktrackingsystem.constants.TaskConstants;
import com.tasktrackingsystem.dto.ErrorResponseDto;
import com.tasktrackingsystem.entity.User;
import com.tasktrackingsystem.serviceimpl.UserServiceImpl;
import com.tasktrackingsystem.util.UserAuthenticationTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAuthenticationHandlerFilter extends OncePerRequestFilter {

    @Autowired
    private UserAuthenticationTokenUtil jwttokenUtil;

    @Autowired
    private UserServiceImpl userService;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        logger.info("UserAuthenticationHandlerFilter: Request Start");
        final String authorizationHeader = request.getHeader(TaskConstants.AUTHORIZATION);
        String jwtToken = null;
        String email = null;

        if (authorizationHeader != null && authorizationHeader.startsWith(TaskConstants.BEARER)) {
            jwtToken = authorizationHeader.substring(7);
            email = jwttokenUtil.extractUserEmail(jwtToken); 
        }

        if (jwtToken != null && userService.isTokenBlacklisted(jwtToken)) {
            sendErrorResponse(response, TaskConstants.INVALID_TOKEN_MESSAGE);
            logger.info("UserAuthenticationHandlerFilter: Unauthorized access - Token is blacklisted");
            return;
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User userDetails = userService.findByEmail(email);
            if (userDetails != null && jwttokenUtil.validateToken(jwtToken)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                sendErrorResponse(response, TaskConstants.INVALID_TOKEN_MESSAGE);
                logger.info("UserAuthenticationHandlerFilter: Unauthorized access - Invalid token or user");
                return; 
            }
        }
        filterChain.doFilter(request, response);
        logger.info("UserAuthenticationHandlerFilter: Response end"); 
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(TaskConstants.APPLICATION_JSON);
        PrintWriter out = response.getWriter();
        ErrorResponseDto errorResponse = new ErrorResponseDto(TaskConstants.UNAUTHORIZED, message);
        ObjectMapper objectMapper = new ObjectMapper();
        out.print(objectMapper.writeValueAsString(errorResponse));
        out.flush();
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String url = request.getRequestURI();
        return (ArrayUtils.contains(SecurityConfig.WHITELISTED_API,url));
    }

}