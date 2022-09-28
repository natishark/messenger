package com.messenger.mess.model.repository;

import com.messenger.mess.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByLogin(String login);
    Optional<User> findByLogin(String login);
}
