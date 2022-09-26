package com.messenger.mess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.mess.model.dtos.UserSignUpDto;
import com.messenger.mess.model.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.function.Consumer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTests {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserControllerIntegrationTests(
            MockMvc mockMvc,
            ObjectMapper objectMapper,
            UserRepository userRepository
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    @Test
    public void signUpCorrectUserTest() throws Exception {
        final var dto = generateRandomUserSignUpDto();
        sendSignUpPostRequest(dto)
                .andExpect(status().isOk())
                .andExpect(content().string(
                                objectMapper.writeValueAsString(
                                        userRepository
                                                .findAll()
                                                .stream()
                                                .filter(user -> user.getLogin().equals(dto.getLogin()))
                                                .findFirst()
                                                .orElseThrow(() -> new AssertionError("User not found"))
                                )
                        )
                );
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void signUpUserWithEmptyLogin(String emptyLogin) throws Exception {
        signUpInvalidUserExpectingBadRequestAndMessage(
                dto -> dto.setLogin(emptyLogin),
                "Login is required."
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void signUpUserWithEmptyPassword(String emptyPassword) throws Exception {
        final var dto = generateRandomUserSignUpDto();
        dto.setPassword(emptyPassword);
        sendSignUpPostRequest(dto)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void signUpUserWithNonVacantLogin() throws Exception {
        signUpInvalidUserExpectingBadRequestAndMessage(
                dto -> {
                    try {
                        sendSignUpPostRequest(dto).andExpect(status().isOk());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                "This login is already in use."
        );
    }

    @Test
    public void signUpUserWithWrongPasswordConfirmation() throws Exception {
        signUpInvalidUserExpectingBadRequestAndMessage(
                dto -> dto.setPasswordConfirmation(RandomString.make(20)),
                "Password confirmation is incorrect."
        );
    }

    private UserSignUpDto generateRandomUserSignUpDto() {
        UserSignUpDto dto = new UserSignUpDto();
        dto.setLogin(RandomString.make(10));
        dto.setPassword(RandomString.make(20));
        dto.setEmail(RandomString.make(20));
        dto.setPasswordConfirmation(dto.getPassword());
        return dto;
    }

    private void signUpInvalidUserExpectingBadRequestAndMessage(Consumer<UserSignUpDto> action, String message) throws Exception {
        final var dto = generateRandomUserSignUpDto();
        action.accept(dto);
        sendSignUpPostRequest(dto)
                .andExpect(status().isBadRequest())
                .andExpect(content().string(message));
    }

    private ResultActions sendSignUpPostRequest(UserSignUpDto dto) throws Exception {
        return mockMvc.perform(
                post("/api/v1/user/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
        );
    }
}
