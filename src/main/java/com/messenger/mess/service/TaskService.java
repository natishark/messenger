package com.messenger.mess.service;

import com.messenger.mess.controller.exception_handling.ValidationFailedException;
import com.messenger.mess.model.Task;
import com.messenger.mess.model.User;
import com.messenger.mess.model.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task saveTask(Task task, User user) {
        task.setUser(user);
        if (task.getStartTime() == null) {
            task.setStartTime(LocalDateTime.now());
        }
        if (task.getStartTime().isAfter(task.getFinishTime())) {
            throw new ValidationFailedException("Хули у тебя время начала позже времени конца...");
        }
        return taskRepository.save(task);
    }
}
