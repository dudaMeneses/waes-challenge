package com.eduardo.waes.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention( RetentionPolicy.RUNTIME)
@Constraint( validatedBy = IsBase64Impl.class)
public @interface IsBase64 {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
