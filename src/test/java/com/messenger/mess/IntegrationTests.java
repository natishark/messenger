package com.messenger.mess;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.mess.model.User;
import com.messenger.mess.model.repository.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.function.Function;
import java.util.function.Predicate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class IntegrationTests {
    protected final MockMvc mockMvc;
    protected final ObjectMapper objectMapper;
    protected final UserRepository userRepository;

    public IntegrationTests(
            MockMvc mockMvc,
            ObjectMapper objectMapper,
            UserRepository userRepository
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    protected User generateRandomUser() {
        User user = new User();
        user.setLogin(RandomString.make(10));
        user.setEmail(RandomString.make(20));
        user.setPassword(RandomString.make(20));
        return user;
    }

    public <T extends JpaRepository<E, Long>, E, R> void expectOkSavedAndContentFromRepository(
            ResultActions resultActions,
            T repository,
            Predicate<E> isDesiredValue,
            Function<E, R> savedToExpected
    ) throws Exception {
        resultActions
                .andExpect(status().isOk())
                .andExpect(
                        content().string(
                                objectMapper.writeValueAsString(
                                        savedToExpected.apply(
                                                repository
                                                        .findAll()
                                                        .stream()
                                                        .filter(isDesiredValue)
                                                        .findFirst()
                                                        .orElseThrow(
                                                                () -> new AssertionError("The saved record was not found")
                                                        )
                                        )
                                )
                        )
                );
    }

    protected void expectBadRequestAndMessage(ResultActions resultActions, String message) throws Exception {
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(content().string(message));
    }

    protected <T> ResultActions sendPostRequest(String requestUrl, T body) throws Exception {
        return mockMvc.perform(
                post(requestUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
        );
    }
}
