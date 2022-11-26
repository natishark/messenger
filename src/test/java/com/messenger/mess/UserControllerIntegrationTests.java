package com.messenger.mess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.mess.model.dtos.UserSignUpDto;
import com.messenger.mess.model.repository.UserRepository;
import com.messenger.mess.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerIntegrationTests extends IntegrationTests {
    private final UserService userService;

    @Autowired
    public UserControllerIntegrationTests(
            MockMvc mockMvc,
            ObjectMapper objectMapper,
            UserRepository userRepository,
            UserService userService
    ) {
        super(mockMvc, objectMapper, userRepository);
        this.userService = userService;
    }

    @Test
    @Transactional
    public void signUpCorrectUserTest() throws Exception {
        final var dto = generateRandomUserSignUpDto();
        expectOkSavedAndContentFromRepository(
                sendSignUpPostRequest(dto),
                userService,
                user -> user,
                service -> service.findByLogin(dto.getLogin())
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
        expectBadRequest(sendSignUpPostRequest(dto));
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

    private void signUpInvalidUserExpectingBadRequestAndMessage (
            Consumer<UserSignUpDto> action, String message
    ) throws Exception {
        sendInvalidEntityExpectingBadRequestAndMessage(
                action,
                message,
                this::generateRandomUserSignUpDto,
                this::sendSignUpPostRequest
        );
    }

    private ResultActions sendSignUpPostRequest(UserSignUpDto dto) {
        try {
            return sendPostRequest("/api/v1/user/sign-up", dto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
