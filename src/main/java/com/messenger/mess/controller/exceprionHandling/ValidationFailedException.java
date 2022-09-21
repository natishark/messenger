package com.messenger.mess.controller.exceprionHandling;

public class ValidationFailedException extends Exception {
    public ValidationFailedException(String message) {
        super(message);
    }
}
