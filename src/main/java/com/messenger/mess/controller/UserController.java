package com.messenger.mess.controller;

import com.messenger.mess.controller.exception_handling.ResourceAlreadyExistsException;
import com.messenger.mess.controller.exception_handling.ValidationFailedException;
import com.messenger.mess.model.User;
import com.messenger.mess.model.dtos.UserSignUpDto;
import com.messenger.mess.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final ConversionService conversionService;

    public UserController(
            UserService userService,
            @Qualifier("mvcConversionService")
            ConversionService conversionService
    ) {
        this.userService = userService;
        this.conversionService = conversionService;
    }

    @PostMapping("/sign-up")
    public User signUp(@Valid @RequestBody UserSignUpDto signUpDto) {
        if (userService.existsByLogin(signUpDto.getLogin())) {
            throw new ResourceAlreadyExistsException("This login is already in use.");
        }
        if (!signUpDto.getPassword().equals(signUpDto.getPasswordConfirmation())) {
            throw new ValidationFailedException("Password confirmation is incorrect.");
        }
        return userService.saveUser(conversionService.convert(signUpDto, User.class));
    }
}
