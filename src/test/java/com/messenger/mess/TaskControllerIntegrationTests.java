package com.messenger.mess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.mess.model.dtos.TaskDto;
import com.messenger.mess.model.repository.TaskRepository;
import com.messenger.mess.model.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;

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

    @Test
    public void createTaskWithWrongUser() throws Exception {
        createInvalidTaskExpectingBadRequestAndMessage(
                dto -> dto.setLogin(RandomString.make(20)),
                "No such user."
        );
    }

    @ParameterizedTest
    @MethodSource("setNullTaskRequiredFields")
    public void createTaskWithNullRequiredField(
            Consumer<TaskDto> changeFieldToNull
    ) throws Exception {
        final var dto = generateRandomTaskDtoWithSavingRandomUser();
        changeFieldToNull.accept(dto);
        expectBadRequest(sendCreateTaskPostRequest(dto));
    }

    @Test
    public void createTaskWithNullStartDateAndPastFinishDate() throws Exception {
        createTaskWithPastFinishDate(null);
    }

    @Test
    public void createTaskWithStartDateAfterPastFinishDate() throws Exception {
        createTaskWithPastFinishDate(LocalDateTime.now());
    }

    private void createTaskWithPastFinishDate(LocalDateTime startTime) throws Exception {
        createInvalidTaskExpectingBadRequestAndMessage(
                dto -> {
                    dto.setStartTime(startTime);
                    dto.setFinishTime(LocalDateTime.parse("2007-12-03T10:15:30"));
                },
                "The startTime must be no later than the finishTime."
        );
    }

    private static Stream<Arguments> setNullTaskRequiredFields() {
        Consumer<TaskDto> setNullLogin = d -> d.setLogin(null);
        Consumer<TaskDto> setNullTitle = d -> d.setTitle(null);
        Consumer<TaskDto> setNullFinishTime = d -> d.setFinishTime(null);
        return Stream.of(
                Arguments.of(setNullLogin),
                Arguments.of(setNullTitle),
                Arguments.of(setNullFinishTime)
        );
    }

    private void createInvalidTaskExpectingBadRequestAndMessage (
            Consumer<TaskDto> action, String message
    ) throws Exception {
        sendInvalidEntityExpectingBadRequestAndMessage(
                action,
                message,
                this::generateRandomTaskDtoWithSavingRandomUser,
                this::sendCreateTaskPostRequest
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

    private ResultActions sendCreateTaskPostRequest(TaskDto dto) {
        try {
            return sendPostRequest("/api/v1/task/create", dto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
