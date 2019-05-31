package com.eduardo.waes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The entity for the informed id has no information in one or more direction")
public class DirectionNotLoadedException extends Exception {
}
