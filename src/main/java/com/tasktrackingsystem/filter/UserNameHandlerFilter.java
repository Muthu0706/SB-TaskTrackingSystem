package com.tasktrackingsystem.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
public class UserNameHandlerFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(UserNameHandlerFilter.class);
    private static final String USER_NAME = "Muthuraj";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("3. UserNameHandlerFilter : Request Start");
        logRequestHeaders(request);
        String RequstHeader = request.getHeader(USER_NAME);
        logger.info("Custom Request Header {}: {}", USER_NAME, (RequstHeader != null ? RequstHeader : "Present")); 
        response.setHeader("X-User-Name", USER_NAME);
        filterChain.doFilter(request, response); 
        logResponseHeaders(response);
        logger.info("3. UserNameHandlerFilter : Response end");
    }

    private void logRequestHeaders(HttpServletRequest request) {
        logger.info("Incoming Request Headers:");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logger.info("  {}: {}", headerName, request.getHeader(headerName));
        }
    }

    private void logResponseHeaders(HttpServletResponse response) {
        logger.info("Outgoing Response Headers:");
        response.getHeaderNames().forEach(headerName ->
                logger.info("  {}: {}", headerName, response.getHeader(headerName))
        );
        logger.info("  X-User-Name: {}", response.getHeader("X-User-Name"));
    }
}