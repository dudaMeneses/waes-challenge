package com.eduardo.waes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <p>I opted to not allow to override values in entities directions once the services for saving are both POST methods</p>
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Value already loaded to the informed direction for this id")
public class DirectionAlreadyLoadedException extends Exception {
}
