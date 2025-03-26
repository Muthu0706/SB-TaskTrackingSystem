package com.tasktrackingsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tasktrackingsystem.entity.ProgressStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDto {
    
    @Schema(example = "101", description = "Unique identifier for the task.")
    @JsonProperty("taskId")
    private String taskId;

    @Schema(example = "Implement user authentication", description = "Description of the task.")
    @JsonProperty("taskDescription")
    @Size(max = 255, message = "Task description must not exceed 255 characters")
    @NotBlank(message="TaskDescripuon is not blank")
    private String taskDescription;

    @Schema(example = "1", description = "User ID of the person assigned to the task.")
    @JsonProperty("userid")
    private Long userid;

    @Schema(example = "OPEN", description = "Current status of the task.")
    @JsonProperty("type")
    private ProgressStatus type;

}