package com.messenger.mess.model.dtos;

import javax.validation.constraints.NotBlank;

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
}
