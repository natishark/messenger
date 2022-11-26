package com.messenger.mess.model.dtos;

import javax.validation.constraints.NotBlank;

public class FriendRequestDto {
    @NotBlank
    private String fromUserLogin;

    @NotBlank
    private String toUserLogin;

    public String getFromUserLogin() {
        return fromUserLogin;
    }

    public void setFromUserLogin(String fromUserLogin) {
        this.fromUserLogin = fromUserLogin;
    }

    public String getToUserLogin() {
        return toUserLogin;
    }

    public void setToUserLogin(String toUserLogin) {
        this.toUserLogin = toUserLogin;
    }
}
