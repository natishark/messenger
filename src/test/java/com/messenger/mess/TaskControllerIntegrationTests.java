package com.messenger.mess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.mess.model.dtos.TaskDto;
import com.messenger.mess.model.repository.TaskRepository;
import com.messenger.mess.model.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TaskControllerIntegrationTests extends IntegrationTests {

    private final TaskRepository taskRepository;
    private final ConversionService conversionService;


    @Autowired
    public TaskControllerIntegrationTests(
            MockMvc mockMvc,
            ObjectMapper objectMapper,
            UserRepository userRepository,
            TaskRepository taskRepository,
            @Qualifier("mvcConversionService")
            ConversionService conversionService
    ) {
        super(mockMvc, objectMapper, userRepository);
        this.taskRepository = taskRepository;
        this.conversionService = conversionService;
    }

    @Test
    public void createCorrectTaskTest() throws Exception {
        final var dto = generateRandomTaskDtoWithSavingRandomUser();
        expectOkSavedAndContentFromRepository(
                sendCreateTaskPostRequest(dto),
                taskRepository,
                task -> task.getTitle().equals(dto.getTitle()),
                task -> conversionService.convert(task, TaskDto.class)
        );
    }

    private TaskDto generateRandomTaskDtoWithSavingRandomUser() {
        TaskDto dto = new TaskDto();
        dto.setLogin(userRepository.save(generateRandomUser()).getLogin());
        dto.setTitle(RandomString.make(50));
        dto.setDescription(RandomString.make(50));
        dto.setStartTime(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        dto.setFinishTime(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        return dto;
    }

    private ResultActions sendCreateTaskPostRequest(TaskDto dto) throws Exception {
        return sendPostRequest("/api/v1/task/create", dto);
    }
}
