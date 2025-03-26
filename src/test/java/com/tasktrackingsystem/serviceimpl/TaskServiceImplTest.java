package com.tasktrackingsystem.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.tasktrackingsystem.dto.TaskRequestDto;
import com.tasktrackingsystem.entity.ProgressStatus;
import com.tasktrackingsystem.entity.Task;
import com.tasktrackingsystem.entity.User;
import com.tasktrackingsystem.mapper.TaskTrackingSystemMapper;
import com.tasktrackingsystem.repository.TaskRepository;
import com.tasktrackingsystem.repository.UserRepository;
import com.tasktrackingsystem.testutils.TaskTestUtils;
import com.tasktrackingsystem.testutils.UserTestUtils;

@ExtendWith(SpringExtension.class)
public class TaskServiceImplTest {
	
	@InjectMocks
	private TaskServiceImpl taskServiceImplTest;
	
	@Mock
	private TaskRepository taskRepository;
	
	@Mock
    private UserRepository userRepository;
		
	@Mock
    private TaskTrackingSystemMapper taskTrackingSystemMapper;
		
	@Test
	void testaddTask() {
	    User mockUser = UserTestUtils.MockUser();
	    TaskRequestDto mockTaskRequestDto = TaskTestUtils.MocktaskRequestDto();
	    Task mockTask = TaskTestUtils.mockTask();
	    mockTask.setTaskId(1L); 
	    Mockito.when(taskTrackingSystemMapper.taskRequestDtoToTask(any(TaskRequestDto.class), 
	            eq(ProgressStatus.OPEN), 
	            any(User.class))).thenReturn(mockTask);
	    Mockito.when(taskRepository.save(any(Task.class))).thenReturn(mockTask);
	    Task savedTask = taskServiceImplTest.addTask(mockTaskRequestDto, mockUser);
	    assertEquals(mockTask.getTaskId(), savedTask.getTaskId(), "The returned task ID should match the expected task ID");
	    verify(taskTrackingSystemMapper, times(1)).taskRequestDtoToTask(mockTaskRequestDto, ProgressStatus.OPEN, mockUser);
	    verify(taskRepository, times(1)).save(mockTask);
	}
			
	@Test
	void testdeleteTask() {
	    Task mockTask = TaskTestUtils.mockTask();

	    Long userId = 1L;
	    mockTask.setTaskId(1L); 
	    Mockito.when(taskRepository.findById(mockTask.getTaskId())).thenReturn(Optional.of(mockTask)); 
	    taskServiceImplTest.deleteTask(mockTask.getTaskId(),userId); 
	    verify(taskRepository, times(1)).findById(mockTask.getTaskId()); 
	    verify(taskRepository, times(1)).deleteById(mockTask.getTaskId()); 
	}

	@Test
	void testupdateProgressStatus() {
	    Task mockTask = TaskTestUtils.mockTask();
	    long userId = 2 ;
	    User mockUser = UserTestUtils.MockUser();
	    mockUser.getUserid();
	    Mockito.when(taskRepository.findById(mockTask.getTaskId())).thenReturn(Optional.of(mockTask));
	    taskServiceImplTest.updateProgressStatus(mockTask.getTaskId(),mockTask.getTaskDescription(), ProgressStatus.INPROGRESS, userId);

	    verify(taskRepository, times(1)).save(mockTask); 
	    assertEquals(ProgressStatus.INPROGRESS, mockTask.getType(), "The task's progress status should be updated.");
	}
		
	@Test
	void testUserTasksAreRetrievedAndDeleted() {
	    User mockUser = UserTestUtils.MockUser();
	    List<Task> mockTasks = TaskTestUtils.mockTasksForUser(mockUser); 
	    Mockito.when(userRepository.findById(mockUser.getUserid())).thenReturn(Optional.of(mockUser));
	    Mockito.when(taskRepository.findByUser(mockUser)).thenReturn(mockTasks);
	    doNothing().when(taskRepository).deleteByUser(mockUser); 
	    doNothing().when(userRepository).delete(mockUser); 
	    taskServiceImplTest.deleteUserAndTasks(mockUser.getUserid());
	    verify(taskRepository, times(1)).deleteByUser(mockUser); 
	    verify(userRepository, times(1)).delete(mockUser); 
	}
	
	@Test
	void testgetAllTasksByUserId() {
	    User mockUser = UserTestUtils.MockUser();
	    List<Task> mockTasks = TaskTestUtils.mockTasksForUser(mockUser); 
	    Mockito.when(userRepository.findById(mockUser.getUserid())).thenReturn(Optional.of(mockUser));
	    Mockito.when(taskRepository.findByUser(mockUser)).thenReturn(mockTasks);
	    taskServiceImplTest.getAllTasksByUserId(mockUser.getUserid());
	    verify(taskRepository, times(1)).findByUser(mockUser); 
	    verify(userRepository, times(1)).findById(mockUser.getUserid()); 
	}
	
}
