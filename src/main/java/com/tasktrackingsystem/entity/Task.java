package com.tasktrackingsystem.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name= "task")
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable=true)
    private String taskDescription;
    
    @Enumerated(EnumType.STRING) 
    private ProgressStatus type;
    
    private LocalDateTime createdAt; 
    
    private LocalDateTime completedAt;
    
    @ManyToOne
    @JoinColumn(name = "userid") 
    private User user;
       
}
