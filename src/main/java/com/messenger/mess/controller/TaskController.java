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
            @RequestParam(value = "login") String login
    ) {
        User user = userService.findByLogin(login).orElseThrow(() -> new ValidationFailedException("No such user."));
        final var task = taskService.saveTask(
                Objects.requireNonNull(conversionService.convert(taskDto, Task.class)),
                user
        );
        return conversionService.convert(
                task,
                TaskDto.class
        );
    }
}
