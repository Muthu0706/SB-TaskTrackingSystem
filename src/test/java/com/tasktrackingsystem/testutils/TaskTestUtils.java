package com.tasktrackingsystem.testutils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.tasktrackingsystem.dto.TaskRequestDto;
import com.tasktrackingsystem.entity.ProgressStatus;
import com.tasktrackingsystem.entity.Task;
import com.tasktrackingsystem.entity.User;
import com.tasktrackingsystem.testconstant.TaskTestConstants;

public class TaskTestUtils {

	public static User MockUser() {
		User user = new User();
        user.setUserid(1L);
		user.setUsername(TaskTestConstants.MOCK_USERNAME);

		user.setEmail(TaskTestConstants.MOCK_EMAIL);
		user.setPassword(TaskTestConstants.MOCK_PASSWORD);
		user.setPhonenumber(1234567890L);
	    byte[] mockProfilePicture = "mockPicture".getBytes(StandardCharsets.UTF_8);
	    user.setProfilePicture(mockProfilePicture); 
		user.setRole(TaskTestConstants.MOCK_ROLE_TYPE);
		return user;
	}
	
	public static Task mockTask() {
		Task mockTask = new Task();
		User user = new User();
		mockTask.setTaskId(1L);
		mockTask.setTaskDescription(TaskTestConstants.MOCK_TASK_DESCRIPTION);
		mockTask.setCompletedAt(LocalDateTime.now());
		mockTask.setCreatedAt(LocalDateTime.now());
		mockTask.setType(ProgressStatus.OPEN);
		mockTask.setUser(user);
		return mockTask;
	}

	public static TaskRequestDto MocktaskRequestDto() {
		TaskRequestDto taskRequestDto = new TaskRequestDto();
		taskRequestDto.setTaskDescription(TaskTestConstants.MOCK_TASK_DESCRIPTION);
		taskRequestDto.setType(ProgressStatus.OPEN);
		taskRequestDto.setUserid(1L);
		return taskRequestDto;
		
	}
	
	public static Task MockEditTask() {
    Task editTask = new Task();
    Task existingTask = TaskTestUtils.mockTask(); 
    editTask.setTaskId(existingTask.getTaskId());
    editTask.setTaskDescription("Updated Task Description");
    editTask.setType(ProgressStatus.OPEN);
	return editTask;
	}
	
	public static List<Task> mockTasksForUser(User user) {
	    List<Task> mockTaskList = new ArrayList<>();
	    for (int i = 0; i < 5; i++) {
	        Task mockTask = new Task();
	        mockTask.setTaskId(1L); 
			mockTask.setTaskDescription(TaskTestConstants.MOCK_TASK_DESCRIPTION);
			mockTask.setCompletedAt(LocalDateTime.now());
			mockTask.setCreatedAt(LocalDateTime.now());
			mockTask.setType(ProgressStatus.OPEN);
			mockTask.setUser(user);
	        mockTaskList.add(mockTask);
	    }
	    return mockTaskList;
	}
} 
