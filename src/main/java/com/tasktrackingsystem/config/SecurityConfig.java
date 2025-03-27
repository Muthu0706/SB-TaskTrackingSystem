package com.tasktrackingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.tasktrackingsystem.exception.CustomUnauthorizedEntryPoint;
import com.tasktrackingsystem.filter.UserLogHandlerFilter;
import com.tasktrackingsystem.filter.CORSFilter;
import com.tasktrackingsystem.filter.UserAuthenticationHandlerFilter;
import com.tasktrackingsystem.filter.UserNameHandlerFilter;
import lombok.RequiredArgsConstructor;
import com.tasktrackingsystem.constants.TaskConstants;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserLogHandlerFilter userLogHandlerFilter;
    private final UserAuthenticationHandlerFilter userAuthenticationFilter;
    private final UserNameHandlerFilter userNameHandlerFilter;
    private final CustomUnauthorizedEntryPoint customUnauthorizedEntryPoint;
    private final CORSFilter corsFilter; 
    
	public static final String[] WHITELISTED_API = {
			"/v3/api-docs/",
			"http://localhost:8080/v3/api-docs",
            "/swagger-ui/",
	  TaskConstants.TASK_TRACKING_SYSTEM_UI_API + "/signup", 
          TaskConstants.TASK_TRACKING_SYSTEM_UI_API + "/login",
            "/swagger-ui.html",
            "/api/auth/signup",
            "/api/auth/login",
            "/api/auth/force-run-task",
            "/swagger-ui.html",
            "/swagger-ui/index.html",
            "/swagger-ui/index.css",
            "/swagger-ui/swagger-ui.css",
            "/swagger-ui/swagger-initializer.js",
            "/swagger-ui/swagger-ui-bundle.js",
            "/swagger-ui/swagger-ui-standalone-preset.js",
            "/swagger-ui/favicon-32x32.png",
            "/api-docs",
            "/api-docs/swagger-config",
            
	};
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> request
                .requestMatchers(WHITELISTED_API).permitAll() 
                .anyRequest().authenticated())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(customUnauthorizedEntryPoint));
        
        http.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(userNameHandlerFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(userAuthenticationFilter, UserNameHandlerFilter.class)
            .addFilterBefore(userLogHandlerFilter, UserAuthenticationHandlerFilter.class);
        
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

