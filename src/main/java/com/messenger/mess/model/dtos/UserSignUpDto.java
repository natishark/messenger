package com.messenger.mess.model.dtos;

import com.messenger.mess.model.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserSignUpDto {
    @NotBlank(message = "Login is required.")
    private String login;
    private String email;
    @NotBlank(message = "Password is required.")
    private String password;
    @NotBlank(message = "Password confirmation is required.")
    private String passwordConfirmation;

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public User toUser() {
        return new User(login, email, password);
    }
}
