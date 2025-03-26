package com.tasktrackingsystem.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.tasktrackingsystem.constants.TaskConstants;
import com.tasktrackingsystem.dto.TaskRequestDto;
import com.tasktrackingsystem.dto.TaskResponseDto;
import com.tasktrackingsystem.entity.ProgressStatus;
import com.tasktrackingsystem.entity.User;
import com.tasktrackingsystem.serviceimpl.TaskServiceImpl;
import com.tasktrackingsystem.util.UserAuthenticationTokenUtil;
import com.tasktrackingsystem.util.UserServiceImplUtil;
import jakarta.servlet.http.HttpServletRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping(TaskConstants.TASK_REQUESTMAPPING)
@RequiredArgsConstructor
@Tag(name = TaskConstants.TASK_MANAGEMENT_NAME,
description = TaskConstants.TASK_DESCRIPTION)
public class TaskController {

    private final TaskServiceImpl taskService;
    private final UserAuthenticationTokenUtil jwtUtil;
    private final UserServiceImplUtil userServiceImplUtil;
    
    // Add Task :
    @PostMapping(TaskConstants.ADD_TASK_DETAILS)
    @Operation(summary = TaskConstants.ADD_NEW_TASK, description= TaskConstants.ADD_NEW_TASK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = TaskConstants.TASK_ADD_SUCCESS_ERROR_CODE,
                    description = TaskConstants.TASK_ADD_SUCCESS_MESSAGE)
    })
    public ResponseEntity<Void> addTask(@RequestBody TaskRequestDto taskRequest, HttpServletRequest request) {
        User user = userServiceImplUtil.authenticateUser(request);
        taskService.addTask(taskRequest, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    // Update Task :
    @PutMapping(TaskConstants.PROGRESS_STATUS)
    @Operation(summary = TaskConstants.UPDATE_TASKPROGRESS_STATUS, description = TaskConstants.UPDATE_TASKPROGRESS_STATUS)
    @ApiResponses({
        @ApiResponse(responseCode = TaskConstants.CREATED_NO_CONTENT, description = TaskConstants.PROGRESS_STATUS_UPDATE_SUCCESS_MESSAGE),
        @ApiResponse(responseCode = TaskConstants.TASK_NOT_FOUND_ERROR, description = TaskConstants.TASK_NOT_FOUND_MESSAGE)
    })
    public ResponseEntity<Void> updateProgressStatus(@PathVariable Long taskId, 
                                                      @RequestParam(required = false) String taskDescription,
                                                      @RequestParam(required = false) ProgressStatus type,
                                                      HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwtToken = UserAuthenticationTokenUtil.extractJwtToken(authHeader);
        Long userId = jwtUtil.extractUserId(jwtToken);
        taskService.updateProgressStatus(taskId, taskDescription, type, userId);
        return ResponseEntity.noContent().build();
    }
    
    // Delete Task
    @DeleteMapping(TaskConstants.DELETE_BY_TASKID)
    @Operation(summary = TaskConstants.DETETE_TASK_BY_ID, description = TaskConstants.DETETE_TASK_BY_ID)
    @ApiResponses({
        @ApiResponse(responseCode = TaskConstants.CREATED_NO_CONTENT, description = TaskConstants.TASK_DELETE_SUCCESS_MESSAGE),
        @ApiResponse(responseCode = TaskConstants.TASK_NOT_FOUND_ERROR, description = TaskConstants.TASK_NOT_FOUND_MESSAGE),
        @ApiResponse(responseCode = TaskConstants.INVALID_CREDENTIALS_ERROR_CODE, description = TaskConstants.UNAUTHORIZED_EDIT) // Add this for unauthorized access
    })
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId, HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwtToken = UserAuthenticationTokenUtil.extractJwtToken(authHeader);
        Long userId = jwtUtil.extractUserId(jwtToken);
        taskService.deleteTask(taskId, userId);        
        return ResponseEntity.noContent().build();
    }
    
    // Formatted Task :
    @DeleteMapping(TaskConstants.DELETE_TASK_BYUSERID)
    @Operation(summary = TaskConstants.DELETE_USERBY_TASKS, description = TaskConstants.DELETE_USERBY_TASKS)
    @ApiResponses({
        @ApiResponse(responseCode = TaskConstants.CREATED_NO_CONTENT, description = TaskConstants.USER_AND_TASKS_DELETE_SUCCESS_MESSAGE),
        @ApiResponse(responseCode = TaskConstants.TASK_NOT_FOUND_ERROR, description = TaskConstants.USER_NOT_FOUND_MESSAGE)
    })
    public ResponseEntity<Void> deleteUser(HttpServletRequest request) {
        User user = userServiceImplUtil.authenticateUser(request);
        taskService.deleteUserAndTasks(user.getUserid());
        return ResponseEntity.noContent().build();
    }

    // Get TaskList Only return Open and Inprogress :
    @GetMapping(TaskConstants.USER_GET_BY_ID)
    @Operation(summary = TaskConstants.GET_ALL_TASKS_BYUSER, description = TaskConstants.GET_ALL_TASKS_BYUSER)
    @ApiResponses({
        @ApiResponse(responseCode = TaskConstants.TASK_RETRIEVED_SUCCESS_ERROR_CODE, description = TaskConstants.TASK_RETRIEVED_SUCCESS_MESSAGE),
        @ApiResponse(responseCode = TaskConstants.TASK_NOT_FOUND_ERROR, description = TaskConstants.USER_NOT_FOUND_MESSAGE)
    })
    public ResponseEntity<List<TaskResponseDto>> getTasksByUser(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwtToken = UserAuthenticationTokenUtil.extractJwtToken(authHeader);
        Long userId = jwtUtil.extractUserId(jwtToken);
        List<TaskResponseDto> tasksResponse = taskService.getAllTasksByUserId(userId);
        return ResponseEntity.ok(tasksResponse);
    }
    
    // Task return Completed Report and Count - Open and InProgress
    @GetMapping(TaskConstants.TASK_REPORT)
    @Operation(summary = TaskConstants.GET_TASK_REPORTS, description = TaskConstants.GET_TASK_REPORTS)
    @ApiResponses({
        @ApiResponse(responseCode = TaskConstants.TASK_RETRIEVED_SUCCESS_ERROR_CODE, description = TaskConstants.TASK_RETRIEVED_SUCCESS_MESSAGE)
    })
    public ResponseEntity<Map<String, Object>> getTaskReport(HttpServletRequest request) {
        User user = userServiceImplUtil.authenticateUser(request);
        Map<String, Object> report = taskService.getCompletedTaskReport(user.getUserid());
        return ResponseEntity.ok(report);
    }
        
}