package com.tasktrackingsystem.dto;


import lombok.Data;

@Data
public class TaskResponseDto {
	
	  private Long taskId;
	  private String taskDescription;
	  private String type;
	    
}
