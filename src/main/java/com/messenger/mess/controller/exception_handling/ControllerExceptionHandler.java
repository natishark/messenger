package com.messenger.mess.controller.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ValidationFailedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleValidationFailedException(ValidationFailedException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(NoSuchUserException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String handleNoSuchUserException(NoSuchUserException e) {
        return e.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String kek(MethodArgumentNotValidException e) {
        // нормально обработать
        return Objects.requireNonNull(e.getBindingResult().getFieldError("login")).getDefaultMessage();
    }
}
