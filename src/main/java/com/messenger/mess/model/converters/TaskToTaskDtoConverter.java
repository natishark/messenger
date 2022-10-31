package com.messenger.mess.model.converters;

import com.messenger.mess.model.Task;
import com.messenger.mess.model.dtos.TaskDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskToTaskDtoConverter implements Converter<Task, TaskDto> {
    @Override
    public TaskDto convert(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setLogin(task.getUser().getLogin());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStartTime(task.getStartTime());
        taskDto.setFinishTime(task.getFinishTime());
        return taskDto;
    }
}
