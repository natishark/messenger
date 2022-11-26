package com.messenger.mess.controller.exception_handling;

public class NoSuchUserException extends ValidationFailedException {
    public NoSuchUserException(String message) {
        super("No such user: " + message);
    }
}
