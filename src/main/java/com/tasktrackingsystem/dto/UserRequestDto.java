package com.tasktrackingsystem.dto;

import com.tasktrackingsystem.constants.TaskConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder 
public class UserRequestDto {
	
	private Long userid;
	
    @Schema(example = "muthuraj@gmail.com")
    private String email;
    
    @Schema(example = "Password@1234")
    private String password;
    
    @Size(min = 3, max = 15, message = TaskConstants.USERNAME_LENGTH)
    @Schema(example = "muthuraj")
    private String username;
        
    private Long phonenumber;
    
    private byte[] profilePicture;

}
