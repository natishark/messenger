package com.messenger.mess.controller;

import com.messenger.mess.controller.exceprionHandling.ValidationFailedException;
import com.messenger.mess.model.User;
import com.messenger.mess.model.dtos.UserSignUpDto;
import com.messenger.mess.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    private final UserService userService;
    public RegistrationController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public User register(@Valid @RequestBody UserSignUpDto signUpDto) throws ValidationFailedException {
        if (!signUpDto.getPassword().equals(signUpDto.getPasswordConfirmation())) {
            throw new ValidationFailedException("Password confirmation is incorrect");
        }
        if (userService.existsByLogin(signUpDto.getLogin())) {
            throw new ValidationFailedException("This login is already in use");
        }
        return userService.saveUser(signUpDto.toUser());
    }
}
