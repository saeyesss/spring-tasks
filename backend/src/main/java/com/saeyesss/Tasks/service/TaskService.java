package com.saeyesss.Tasks.service;

import com.saeyesss.Tasks.dto.Response;
import com.saeyesss.Tasks.dto.TaskRequest;
import com.saeyesss.Tasks.entity.Task;

import java.util.List;

public interface TaskService {

    Response<Task> createTask(TaskRequest taskRequest);

    Response<Task> updateTask(TaskRequest taskRequest);

    Response<Void> deleteTask(Long id);

    Response<List<Task>> getAllTasks();

    Response<Task> getTaskById(Long id);

    Response<List<Task>> getTasksByCompletionStatus(boolean completed);

    Response<List<Task>> getTasksByPriority(String priority);

}
