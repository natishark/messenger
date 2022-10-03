package com.messenger.mess.model.converters;

import com.messenger.mess.model.Task;
import com.messenger.mess.model.dtos.TaskDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TaskDtoToTaskConverter implements Converter<TaskDto, Task> {
    @Override
    public Task convert(TaskDto dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStartTime(dto.getStartTime());
        task.setFinishTime(dto.getFinishTime());
        return task;
    }
}
