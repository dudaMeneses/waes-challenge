package com.eduardo.waes.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExpcetionHandler {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid direction requested. Please inform left or right")
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public void handleInvalidArgumentException(){}

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not a Base64 encoded parameter")
    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstraintViolationException(){}

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException(){}
}
