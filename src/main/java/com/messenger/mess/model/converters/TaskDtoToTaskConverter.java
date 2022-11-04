package com.messenger.mess.model.converters;

import com.messenger.mess.controller.exception_handling.ValidationFailedException;
import com.messenger.mess.model.Task;
import com.messenger.mess.model.dtos.TaskDto;
import com.messenger.mess.service.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskDtoToTaskConverter implements Converter<TaskDto, Task> {
    private final UserService userService;

    public TaskDtoToTaskConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Task convert(TaskDto dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStartTime(dto.getStartTime() == null
                ? LocalDateTime.now()
                : dto.getStartTime()
        );
        task.setFinishTime(dto.getFinishTime());
        if (task.getStartTime().isAfter(task.getFinishTime())) {
            throw new ValidationFailedException("The startTime must be no later than the finishTime.");
        }
        task.setUser(
                userService
                        .findByLogin(dto.getLogin())
                        .orElseThrow(() -> new ValidationFailedException("No such user."))
        );
        return task;
    }
}
