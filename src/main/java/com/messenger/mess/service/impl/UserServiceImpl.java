package com.messenger.mess.service.impl;

import com.messenger.mess.model.User;
import com.messenger.mess.model.repository.UserRepository;
import com.messenger.mess.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }
}
