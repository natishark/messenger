package com.messenger.mess.model.converters;

import com.messenger.mess.controller.exception_handling.NoSuchUserException;
import com.messenger.mess.controller.exception_handling.ResourceAlreadyExistsException;
import com.messenger.mess.controller.exception_handling.ValidationFailedException;
import com.messenger.mess.model.FriendRequest;
import com.messenger.mess.model.FriendRequestStatus;
import com.messenger.mess.model.User;
import com.messenger.mess.model.dtos.FriendRequestDto;
import com.messenger.mess.service.FriendRequestService;
import com.messenger.mess.service.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class FriendRequestDtoToFriendRequestConverter implements Converter<FriendRequestDto, FriendRequest> {
    private final UserService userService;
    private final FriendRequestService friendRequestService;

    public FriendRequestDtoToFriendRequestConverter(
            UserService userService,
            FriendRequestService friendRequestService
    ) {
        this.userService = userService;
        this.friendRequestService = friendRequestService;
    }

    @Override
    public FriendRequest convert(FriendRequestDto dto) {
        if (dto.getFromUserLogin().equals(dto.getToUserLogin())) {
            throw new ValidationFailedException("Friend request cannot be sent to self.");
        }

        User fromUser = userService.findByLogin(dto.getFromUserLogin()).orElseThrow(() ->
                new NoSuchUserException(dto.getFromUserLogin())
        );

        User toUser = userService.findByLogin(dto.getToUserLogin()).orElseThrow(() ->
                new NoSuchUserException(dto.getToUserLogin())
        );

        if (friendRequestService
                .existsByFromUserAndToUser(fromUser, toUser)) {
            throw new ResourceAlreadyExistsException("The request was sent earlier.");
        }
        return new FriendRequest()
                .setFromUser(fromUser)
                .setToUser(toUser)
                .setStatus(FriendRequestStatus.REQUESTED);
    }
}
