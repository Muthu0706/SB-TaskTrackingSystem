package com.tasktrackingsystem.controller;

import com.tasktrackingsystem.constants.TaskConstants;
import com.tasktrackingsystem.serviceimpl.ScheduledTaskServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = TaskConstants.TASK_REQUESTMAPPING)
public class ScheduledTaskController {

    private final ScheduledTaskServiceImpl scheduledTaskService;

    public ScheduledTaskController(ScheduledTaskServiceImpl scheduledTaskService) {
        this.scheduledTaskService = scheduledTaskService;
    }

    @GetMapping("/force-run-task")
    public ResponseEntity<String> forceRunTask() {
        scheduledTaskService.scheduledTask();
        return ResponseEntity.ok("Task executed manually.");
    }

}