package com.messenger.mess.controller.exception_handling;

public class ResourceAlreadyExistsException extends ValidationFailedException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
