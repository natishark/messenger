package com.messenger.mess.service;

import com.messenger.mess.controller.exception_handling.ValidationFailedException;
import com.messenger.mess.model.Task;
import com.messenger.mess.model.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
}
