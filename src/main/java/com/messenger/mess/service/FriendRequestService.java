package com.messenger.mess.service;

import com.messenger.mess.model.FriendRequest;
import com.messenger.mess.model.FriendRequestStatus;
import com.messenger.mess.model.User;
import com.messenger.mess.model.repository.FriendRequestRepository;
import org.springframework.stereotype.Service;

@Service
public class FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;
    private final UserService userService;

    public FriendRequestService(FriendRequestRepository friendRequestRepository, UserService userService) {
        this.friendRequestRepository = friendRequestRepository;
        this.userService = userService;
    }

    public boolean existsByFromUserAndToUser(User fromUser, User toUser) {
        return friendRequestRepository.existsByFromUserAndToUser(fromUser, toUser);
    }

    public FriendRequestStatus saveFriendRequest(FriendRequest request) {
        friendRequestRepository
                .findByFromUserAndToUser(request.getToUser(), request.getFromUser())
                .ifPresent((reverseRequest) -> {
                    friendRequestRepository.save(
                            reverseRequest.setStatus(FriendRequestStatus.ACCEPTED)
                    );

                    acceptFriendRequest(request);
                });

        return friendRequestRepository
                .save(request)
                .getStatus();
    }

    public void acceptFriendRequest(FriendRequest request) {
        friendRequestRepository.save(
                request.setStatus(FriendRequestStatus.ACCEPTED)
        );

        userService.addFriend(request.getFromUser(), request.getToUser());
    }
}
