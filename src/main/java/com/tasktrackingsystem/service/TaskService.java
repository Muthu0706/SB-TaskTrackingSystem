package com.tasktrackingsystem.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.tasktrackingsystem.dto.TaskRequestDto;
import com.tasktrackingsystem.dto.TaskResponseDto;
import com.tasktrackingsystem.entity.ProgressStatus;
import com.tasktrackingsystem.entity.Task;
import com.tasktrackingsystem.entity.User;

@Service
public interface TaskService {
	
    Task addTask(TaskRequestDto taskRequest, User user)  ;
        
    void deleteTask(Long taskId,Long userId) ;
    
    List<TaskResponseDto> getAllTasksByUserId(Long userid) ; 
       
    void updateProgressStatus(Long taskId, String taskDescription, ProgressStatus type,Long userId) ;
    
    Map<String, Object> getCompletedTaskReport(Long userId) ;
            
    void deleteUserAndTasks(Long userid);
    
}