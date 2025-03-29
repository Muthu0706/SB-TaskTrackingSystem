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
        registry.addMapping(TaskConstants.CROSSMAPPING) // Allow any path
            .allowedOrigins(TaskConstants.TASK_TRACKING_SYSTEM_UI_API, 
                           TaskConstants.TASK_TRACKING_SYSTEM_PRODUCTION_API) // Allow both dev and prod
            .allowedMethods(TaskConstants.API_MAPPING) // Allowed methods
            .allowedHeaders("*") // Allow all headers
            .allowCredentials(true); // Allow credentials (if needed)
    }
}
// @Configuration
// @EnableWebMvc
// public class WebConfig implements WebMvcConfigurer {
//     @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         registry.addMapping("/api/**") // Adjust this to match your endpoints
//                 .allowedOrigins("http://localhost:3000") // Allow localhost:3000 for development
//                 .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                 .allowedHeaders("*") // Allow all headers
//                 .allowCredentials(false); // Change to true if you need credentials
//     }
// }

