package com.saeyesss.Tasks.service.impl;

import com.saeyesss.Tasks.dto.Response;
import com.saeyesss.Tasks.dto.TaskRequest;
import com.saeyesss.Tasks.entity.Task;
import com.saeyesss.Tasks.entity.User;
import com.saeyesss.Tasks.enums.Priority;
import com.saeyesss.Tasks.exceptions.NotFoundException;
import com.saeyesss.Tasks.repo.TaskRepository;
import com.saeyesss.Tasks.service.TaskService;
import com.saeyesss.Tasks.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public Response<Task> createTask(TaskRequest taskRequest) {
        log.info("Creating a new task");

        User user = userService.getCurrentLoggedInUser();

        Task task = Task.builder()
                .title(taskRequest.getTitle())
                .description(taskRequest.getDescription())
                .completed(taskRequest.getCompleted())
                .priority(taskRequest.getPriority())
                .dueDate(taskRequest.getDueDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .build();

        Task savedTask = taskRepository.save(task);
        return Response.<Task>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Created a new task")
                .data(savedTask)
                .build();
    }

    @Override
    public Response<Task> updateTask(TaskRequest taskRequest) {
        log.info("inside updateTask()");

        Task task = taskRepository.findById(taskRequest.getId())
                .orElseThrow(() -> new NotFoundException("Tasks not found"));

        if (taskRequest.getTitle() != null) task.setTitle(taskRequest.getTitle());
        if (taskRequest.getDescription() != null) task.setDescription(taskRequest.getDescription());
        if (taskRequest.getCompleted() != null) task.setCompleted(taskRequest.getCompleted());
        if (taskRequest.getPriority() != null) task.setPriority(taskRequest.getPriority());
        if (taskRequest.getDueDate() != null) task.setDueDate(taskRequest.getDueDate());
        task.setUpdatedAt(LocalDateTime.now());

        Task updatedTask = taskRepository.save(task);

        return Response.<Task>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Task updated successfully")
                .data(updatedTask)
                .build();
    }

    @Override
    public Response<Void> deleteTask(Long id) {

        log.info("inside deleteTask()");
        if (!taskRepository.existsById(id)) {
            throw new NotFoundException("Task does not exist");
        }
        taskRepository.deleteById(id);

        return Response.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Task deleted successfully")
                .build();
    }

    @Override
    public Response<List<Task>> getAllTasks() {
        log.info("inside getAllMyTasks()");
        User currentUser = userService.getCurrentLoggedInUser();

        List<Task> tasks = taskRepository.findByUser(currentUser, Sort.by(Sort.Direction.DESC, "id"));

        return Response.<List<Task>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Tasks retrieved successfully")
                .data(tasks)
                .build();
    }

    @Override
    public Response<Task> getTaskById(Long id) {
        log.info("inside getTaskById()");

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tasks not found"));

        return Response.<Task>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Task by id retrieved successfully")
                .data(task)
                .build();
    }

    @Override
    public Response<List<Task>> getTasksByCompletionStatus(boolean completed) {
        log.info("inside getTasksByCompletionStatus()");

        User currentUser = userService.getCurrentLoggedInUser();
        List<Task> tasks = taskRepository.findByCompletedAndUser(completed, currentUser);

        return Response.<List<Task>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Tasks by completed status retrieved successfully")
                .data(tasks)
                .build();
    }

    @Override
    public Response<List<Task>> getTasksByPriority(String priority) {
        log.info("inside getTasksByPriority()");

        User currentUser = userService.getCurrentLoggedInUser();

        Priority priorityName = Priority.valueOf(priority.toUpperCase());
        List<Task> tasks = taskRepository.findByPriorityAndUser(priorityName, currentUser);

        return Response.<List<Task>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Tasks by priority retrieved successfully")
                .data(tasks)
                .build();

    }


}
