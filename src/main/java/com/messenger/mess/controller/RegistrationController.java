package com.messenger.mess.controller;

import com.messenger.mess.model.dtos.UserSignUpDto;
import com.messenger.mess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody UserSignUpDto signUpDto) {
        if (!signUpDto.getPassword().equals(signUpDto.getPasswordConfirmation())) {
            return new ResponseEntity<>("Password confirmation is incorrect", HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByLogin(signUpDto.getLogin())) {
            return new ResponseEntity<>("This login is already in use", HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(signUpDto.toUser());
        return new ResponseEntity<>("You were successfully registered", HttpStatus.OK);
    }
}
