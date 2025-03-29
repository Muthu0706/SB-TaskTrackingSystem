package com.tasktrackingsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.tasktrackingsystem.constants.TaskConstants;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping(TaskConstants.CROSSMAPPING) // Add appropriate mapping
	        .allowedOrigins("http://localhost:3000", TaskConstants.TASK_TRACKING_SYSTEM_PRODUCTION_API) // Modify with sources that should be allowed
	        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	        .allowCredentials(true);
	}
}

