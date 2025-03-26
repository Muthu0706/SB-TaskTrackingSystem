package com.tasktrackingsystem.filter;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserLogHandlerFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(UserLogHandlerFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.info("1. UserLogHandlerFilter : Request Start"); 
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            handleException(request, response, ex);
        }
        logger.info("1. UserLogHandlerFilter : Response end"); 
    }

    private void handleException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        logger.error("Exception during request processing: {} {} - {}", request.getMethod(), request.getRequestURI(), ex.getMessage(), ex); // Log the full exception stacktrace
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); 
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"error\": \"An unexpected error occurred.\"}"); 
    }
    
}