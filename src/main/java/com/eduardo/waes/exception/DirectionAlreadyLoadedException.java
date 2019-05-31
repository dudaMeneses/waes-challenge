package com.eduardo.waes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Value already loaded to the informed direction for this id")
public class DirectionAlreadyLoadedException extends Exception {
}
