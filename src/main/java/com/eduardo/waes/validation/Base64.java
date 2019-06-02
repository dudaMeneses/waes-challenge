package com.eduardo.waes.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Annotation to validate Base64 values. In case value isn't a Base64, it will throw a {@link javax.validation.ConstraintViolationException}</p>
 */
@Documented
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention( RetentionPolicy.RUNTIME)
@Constraint( validatedBy = Base64Impl.class)
public @interface Base64 {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
