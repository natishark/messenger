package com.messenger.mess.service;

import com.messenger.mess.model.User;

public interface UserService {
    User saveUser(User user);
    Boolean existsByLogin(String login);
}
