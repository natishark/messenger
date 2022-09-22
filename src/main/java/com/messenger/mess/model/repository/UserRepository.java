package com.messenger.mess.model.repository;

import com.messenger.mess.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByLogin(String login);
}
