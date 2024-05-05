package com.gdyd.gdydsupport.annotation;

import com.gdyd.gdydsupport.validator.EmailValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EmailValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {
    String message() default "";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
