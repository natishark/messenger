package com.messenger.mess.controller;

import com.messenger.mess.model.Task;
import com.messenger.mess.model.dtos.TaskDto;
import com.messenger.mess.service.TaskService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
    private final TaskService taskService;
    private final ConversionService conversionService;

    public TaskController(
            TaskService taskService,
            @Qualifier("mvcConversionService")
            ConversionService conversionService
    ) {
        this.taskService = taskService;
        this.conversionService = conversionService;
    }

    @PostMapping("/create")
    public TaskDto create(
            @Valid @RequestBody TaskDto taskDto
    ) {
        return conversionService.convert(
                taskService.saveTask(
                        Objects.requireNonNull(conversionService.convert(taskDto, Task.class))
                ),
                TaskDto.class
        );
    }
}
