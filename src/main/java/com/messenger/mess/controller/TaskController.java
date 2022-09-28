package com.messenger.mess.controller;

import com.messenger.mess.controller.exception_handling.ValidationFailedException;
import com.messenger.mess.model.Task;
import com.messenger.mess.model.User;
import com.messenger.mess.model.dtos.TaskDto;
import com.messenger.mess.service.TaskService;
import com.messenger.mess.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    private final UserService userService;
    private final TaskService taskService;
    private final ConversionService conversionService;

    public TaskController(
            UserService userService,
            TaskService taskService,
            @Qualifier("mvcConversionService")
            ConversionService conversionService
    ) {
        this.userService = userService;
        this.taskService = taskService;
        this.conversionService = conversionService;
    }

    @PostMapping("/create")
    public TaskDto create(
            @Valid @RequestBody TaskDto taskDto,
            @RequestParam(value = "user-login") String login
    ) {
        Optional<User> userOptional = userService.findByLogin(login);
        if (userOptional.isEmpty()) {
            throw new ValidationFailedException("No such user.");
        }
        Task task = conversionService.convert(taskDto, Task.class);
        taskService.saveTask(Objects.requireNonNull(task), userOptional.get());
        return conversionService.convert(
                taskService.saveTask(task, userOptional.get()),
                TaskDto.class
        );
    }
}
