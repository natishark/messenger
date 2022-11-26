package com.messenger.mess.service;

import com.messenger.mess.model.User;
import com.messenger.mess.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public void addFriend(User firstUser, User secondUser) {
        addFriendToFirstUser(firstUser, secondUser);
        addFriendToFirstUser(secondUser, firstUser);
    }

    private void addFriendToFirstUser(User user, User friend) {
        user.getFriends().add(friend);
        saveUser(user);
    }
}
