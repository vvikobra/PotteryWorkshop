package com.example.potteryworkshop.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = FutureDateValidator.class)
public @interface FutureDate {
    String message() default "The date of the event should be later than now";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
