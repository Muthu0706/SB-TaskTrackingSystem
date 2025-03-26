package com.tasktrackingsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tasktrackingsystem.constants.TaskConstants;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class OpenApiConfig {

	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info().title(TaskConstants.API_NAME).version(TaskConstants.API_VERSION))
            .addSecurityItem(new SecurityRequirement().addList(TaskConstants.API_AUTH))
            .components(new io.swagger.v3.oas.models.Components()
                .addSecuritySchemes(TaskConstants.API_AUTH, new SecurityScheme()
                    .type(Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT"))); 
    }
}

    
   