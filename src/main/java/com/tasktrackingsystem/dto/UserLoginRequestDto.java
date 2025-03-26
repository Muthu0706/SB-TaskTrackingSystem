package com.tasktrackingsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequestDto {

    @Schema(example = "muthuraj@gmail.com", description = "Email of the user")
    @JsonProperty("email")
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @Schema(example = "Password@1234", description = "Password of the user")
    @JsonProperty("password")
    @NotBlank(message = "Password must not be blank")
    private String password;

    @Schema(example = "muthuraj", description = "Username of the user (optional)")
    @JsonProperty("username")
    private String username;  
    
}
