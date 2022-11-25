package com.messenger.mess.model.repository;

import com.messenger.mess.model.FriendRequest;
import com.messenger.mess.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    boolean existsByFromUserAndToUser(User fromUser, User toUser);
    Optional<FriendRequest> findByFromUserAndToUser(User fromUser, User toUser);
}
