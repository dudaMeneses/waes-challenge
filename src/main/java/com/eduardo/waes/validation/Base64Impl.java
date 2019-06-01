package com.eduardo.waes.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Base64Impl implements ConstraintValidator<Base64, String> {
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && value.matches("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$");
    }
}
