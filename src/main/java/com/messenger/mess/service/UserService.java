package com.messenger.mess.service;

import com.messenger.mess.model.User;
import com.messenger.mess.model.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }
}
