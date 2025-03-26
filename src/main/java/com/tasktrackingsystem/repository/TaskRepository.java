package com.tasktrackingsystem.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tasktrackingsystem.entity.Task;
import com.tasktrackingsystem.entity.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
    List<Task> findByUser(User user); 
    
    void deleteByUser(User user);
    
}