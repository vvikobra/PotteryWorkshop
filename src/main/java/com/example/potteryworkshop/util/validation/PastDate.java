package com.example.potteryworkshop.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Constraint(validatedBy = PastDateValidator.class)
public @interface PastDate {
    String message() default "the date of the potter's employment should be earlier than now";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
