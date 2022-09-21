package com.messenger.mess.controller.exceprionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ValidationFailedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody String badRequestException(ValidationFailedException e) {
        return e.getMessage();
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public @ResponseBody String methodArgumentNotValidException(MethodArgumentNotValidException e) {
//        return e.getBindingResult().getFieldError("errors").getDefaultMessage();
//        return e.getFieldError("defaultMessage").toString();
//    }
}
