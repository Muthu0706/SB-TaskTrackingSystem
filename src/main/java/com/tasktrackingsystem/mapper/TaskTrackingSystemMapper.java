package com.tasktrackingsystem.mapper;

import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.tasktrackingsystem.dto.TaskRequestDto;
import com.tasktrackingsystem.dto.UserRequestDto;
import com.tasktrackingsystem.entity.ProgressStatus;
import com.tasktrackingsystem.entity.Task;
import com.tasktrackingsystem.entity.User;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface TaskTrackingSystemMapper {
       
    @Mapping(target = "email", source = "email")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "phonenumber", source = "phonenumber")
    @Mapping(target = "profilePicture", source = "profilePicture" )
    User userRequestDtoToUser(UserRequestDto userRequestDto);
    
    @Mapping(target = "taskId", ignore = true)
	@Mapping(target = "taskDescription", source = "taskRequestDto.taskDescription")
	@Mapping(target = "type", source = "open")
	@Mapping(target = "user", source = "user")
	@Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    Task taskRequestDtoToTask(TaskRequestDto taskRequestDto, ProgressStatus open, User user);
        
}