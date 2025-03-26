package com.tasktrackingsystem.serviceimpl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTaskServiceImpl.class);

    // This method will run every 5 minutes
    @Scheduled(fixedRate = 300000) // 300000 milliseconds = 5 minutes
    public void scheduledTask() {
        logger.info("Scheduled task executed at: {}", System.currentTimeMillis());
    }
}