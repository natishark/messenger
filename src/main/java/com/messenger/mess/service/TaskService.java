package com.messenger.mess.service;

import com.messenger.mess.model.Task;
import com.messenger.mess.model.User;
import com.messenger.mess.model.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task saveTask(Task task, User user) {
        task.setUser(user);
        if (task.getStartDate() == null) {
            task.setStartDate(LocalDate.now());
        }
        return taskRepository.save(task);
    }
}
