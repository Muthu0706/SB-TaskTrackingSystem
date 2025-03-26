package com.tasktrackingsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupRequestDto {

    @Schema(example = "muthuraj@gmail.com", description = "Email of the user. Must be a valid email format.")
    @JsonProperty("email")
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @Schema(example = "Password@1234", description = "Password for the user account. Minimum 8 characters long, with at least one uppercase letter, one lowercase letter, and one special character.")
    @JsonProperty("password")
    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

}