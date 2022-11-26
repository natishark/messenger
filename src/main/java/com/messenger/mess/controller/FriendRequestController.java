package com.messenger.mess.controller;

import com.messenger.mess.model.FriendRequest;
import com.messenger.mess.model.FriendRequestStatus;
import com.messenger.mess.model.dtos.FriendRequestDto;
import com.messenger.mess.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/friend-request")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;
    private final ConversionService conversionService;

    public FriendRequestController(
            FriendRequestService friendRequestService,
            @Qualifier("mvcConversionService")
            ConversionService conversionService
    ) {
        this.friendRequestService = friendRequestService;
        this.conversionService = conversionService;
    }

    @PostMapping("/send")
    public FriendRequestStatus send(@Valid @RequestBody FriendRequestDto friendRequestDto) {
        return friendRequestService.saveFriendRequest(
                Objects.requireNonNull(conversionService.convert(friendRequestDto, FriendRequest.class))
        );
    }
}
