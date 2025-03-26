package com.tasktrackingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaskTrackingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskTrackingSystemApplication.class, args);
	}

}
