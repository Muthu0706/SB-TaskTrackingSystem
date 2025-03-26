package com.tasktrackingsystem.serviceimpl;

import com.tasktrackingsystem.dto.TaskRequestDto;
import com.tasktrackingsystem.dto.TaskResponseDto;
import com.tasktrackingsystem.constants.TaskConstants;
import com.tasktrackingsystem.dto.TaskReportResponseDto;
import com.tasktrackingsystem.entity.ProgressStatus;
import com.tasktrackingsystem.entity.Task;
import com.tasktrackingsystem.entity.User;
import com.tasktrackingsystem.exception.AppErrors;
import com.tasktrackingsystem.exception.AppException;
import com.tasktrackingsystem.mapper.TaskTrackingSystemMapper;
import com.tasktrackingsystem.repository.TaskRepository;
import com.tasktrackingsystem.repository.UserRepository;
import com.tasktrackingsystem.service.TaskService;
import com.tasktrackingsystem.util.TaskServiceImplUtil;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskTrackingSystemMapper taskTrackingSystemMapper;

    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Override
    public Task addTask(TaskRequestDto taskRequestDto, User user) {
        logger.debug(TaskConstants.ENTERING_ADD_TASK, taskRequestDto, user);

        if (ObjectUtils.isEmpty(taskRequestDto) || user == null) {
            logger.error(TaskConstants.ERROR_TASK_OR_USER_NULL);
            throw new AppException(AppErrors.USER_NOT_NULL, HttpStatus.BAD_REQUEST);
        }
        
        if (taskRequestDto.getTaskDescription() == null || taskRequestDto.getTaskDescription().isEmpty()) {
            logger.error("Task description cannot be null or empty");
            throw new AppException(AppErrors.TASK_DESCRIPTION_NOT_NULL, HttpStatus.BAD_REQUEST);
        }
        logger.info(TaskConstants.ADDING_TASK_LOG, user.getUserid(), taskRequestDto.getTaskDescription());
        Task task = taskTrackingSystemMapper.taskRequestDtoToTask(taskRequestDto, ProgressStatus.OPEN, user);
        Task savedTask = taskRepository.save(task);
        logger.info(TaskConstants.TASK_ADDED_SUCCESS_LOG, savedTask.getTaskId());
        return savedTask;
    }
    
    @Override
    public void updateProgressStatus(Long taskId, String taskDescription, ProgressStatus type, Long userId) {
        logger.debug(TaskConstants.ENTERING_UPDATE_PROGRESS_STATUS, taskId, type, userId);
            if (ObjectUtils.isEmpty(taskId) || userId == null) {
            logger.error(TaskConstants.ERROR_TASK_ID_OR_USER_NULL);
            throw new AppException(AppErrors.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> {
                logger.error(TaskConstants.ERROR_TASK_NOT_FOUND_EDIT, taskId);
                return new AppException(AppErrors.TASK_NOT_FOUND, HttpStatus.BAD_REQUEST);
            });
        if (!task.getUser().getUserid().equals(userId)) {
            logger.error(TaskConstants.ERROR_UNAUTHORIZED_UPDATE, userId, taskId);
            throw new AppException(AppErrors.UNAUTHORIZED_EDIT, HttpStatus.FORBIDDEN);
        }
        boolean isDescriptionProvided = taskDescription != null && !taskDescription.isEmpty();
        boolean isProgressStatusProvided = type != null;
        if (isDescriptionProvided) {
            task.setTaskDescription(taskDescription);
        }
        if (isProgressStatusProvided) {
            task.setType(type);
            if (type == ProgressStatus.COMPLETED) {
                task.setCompletedAt(LocalDateTime.now());
                logger.info(TaskConstants.TASK_MARKED_AS_COMPLETED_LOG, taskId);
            }
        } else {
            if (!isDescriptionProvided) {
                logger.error(TaskConstants.ERROR_BOTH_TASKDESCRIPTION_AND_PROGRESSSTATUS_NULL);
                throw new AppException(AppErrors.TASK_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }
        }
        taskRepository.save(task);
        logger.info(TaskConstants.TASK_PROGRESS_STATUS_UPDATED_LOG, taskId);
    }  
    
    @Override
    public void deleteTask(Long taskId, Long userId) {
        logger.debug(TaskConstants.ENTERING_DELETE_TASK, taskId);
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> {
                logger.error(TaskConstants.ERROR_TASK_NOT_FOUND_DELETE, taskId);
                return new AppException(AppErrors.TASK_NOT_FOUND, HttpStatus.BAD_REQUEST);
            });

        if (!task.getUser().getUserid().equals(userId)) {
            logger.error(TaskConstants.INVALID_CREDENTIALS_ERROR_CODE, userId, taskId);
            throw new AppException(AppErrors.UNAUTHORIZED_EDIT, HttpStatus.FORBIDDEN);
        }
        taskRepository.deleteById(taskId);
        logger.info(TaskConstants.TASK_DELETED_SUCCESS_LOG, taskId);
    }

    @Override
    @Transactional
    public void deleteUserAndTasks(Long userid) {
        logger.debug(TaskConstants.ENTERING_DELETE_USER_AND_TASKS, userid);

        if (ObjectUtils.isEmpty(userid)) {
            logger.error(TaskConstants.ERROR_USER_NOT_FOUND);
            throw new AppException(AppErrors.USER_NOT_NULL, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findById(userid)
            .orElseThrow(() -> {
                logger.error(TaskConstants.ERROR_USER_NOT_FOUND, userid);
                return new AppException(AppErrors.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
            });

        taskRepository.deleteByUser(user);
        logger.info(TaskConstants.TASKS_DELETED_LOG, userid);
    }

    @Override
    public List<TaskResponseDto> getAllTasksByUserId(Long userId) {
        logger.debug(TaskConstants.ENTERING_GET_ALL_TASKS_BY_USER_ID, userId);

        if (ObjectUtils.isEmpty(userId)) {
            logger.error(TaskConstants.ERROR_USER_NULL);
            throw new AppException(AppErrors.USER_NOT_NULL, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> {
                logger.error(TaskConstants.ERROR_USER_NOT_FOUND, userId);
                return new AppException(AppErrors.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
            });

        List<Task> tasks = taskRepository.findByUser(user);
        logger.info(TaskConstants.TASKS_RETRIEVED_LOG, userId, tasks.size());

        return tasks.stream()
            .filter(task -> task.getType() != null && 
                            (task.getType() == ProgressStatus.OPEN || 
                             task.getType() == ProgressStatus.INPROGRESS)) 
            .map(task -> {
                TaskResponseDto response = new TaskResponseDto();
                response.setTaskId(task.getTaskId());
                response.setTaskDescription(task.getTaskDescription());
                response.setType(task.getType().name()); 
                return response;
            })
            .collect(Collectors.toList());
    }
    
    @Override
    public Map<String, Object> getCompletedTaskReport(Long userId) {
        logger.debug(TaskConstants.ENTERING_GET_COMPLETED_TASK_REPORT, userId);

        if (ObjectUtils.isEmpty(userId)) {
            logger.error(TaskConstants.ERROR_USER_NULL);
            throw new AppException(AppErrors.USER_NOT_NULL, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> {
                logger.error(TaskConstants.ERROR_USER_NOT_FOUND, userId);
                return new AppException(AppErrors.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
            });

        List<Task> allTasks = taskRepository.findByUser(user);
        List<Task> completedTasks = allTasks
            .stream()
            .filter(task -> task.getType() == ProgressStatus.COMPLETED && task.getCompletedAt() != null)
            .collect(Collectors.toList());

        
        long count = allTasks.stream()
            .filter(task -> task.getType() == ProgressStatus.OPEN || task.getType() == ProgressStatus.INPROGRESS)
            .count();

        List<TaskReportResponseDto> report = new ArrayList<>();
        long totalDurationMillis = 0;

        for (Task task : completedTasks) {
            Duration duration = Duration.between(task.getCreatedAt(), task.getCompletedAt());
            long durationMillis = duration.toMillis();
            totalDurationMillis += durationMillis;
            report.add(new TaskReportResponseDto(task.getTaskId(),
                task.getTaskDescription(),
                TaskServiceImplUtil.convertMillisToSignificantTime(durationMillis)
            ));
            logger.debug(TaskConstants.TASK_DURATION_LOG, task.getTaskId(), durationMillis);
        }
        double averageTimeTaken = completedTasks.isEmpty() ? 0 : totalDurationMillis / (double) completedTasks.size();
        long maxExpectedDurationMillis = 3600000; 
        double averageTimePercentage = Math.min((averageTimeTaken / maxExpectedDurationMillis) * 100, 100);
        String averageTimePercentageDisplay = String.format("%.2f%%", averageTimePercentage);
        Map<String, Object> response = new HashMap<>();
        response.put("averageTimePercentage", averageTimePercentageDisplay);
        response.put("tasks", report);
        response.put("OpenToInProgresscount", count);  
        logger.info(TaskConstants.COMPLETED_TASK_REPORT_GENERATED_LOG, userId);
        return response;
    }

}