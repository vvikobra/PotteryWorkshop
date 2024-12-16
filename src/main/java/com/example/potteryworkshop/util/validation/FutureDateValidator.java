package com.example.potteryworkshop.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FutureDateValidator implements ConstraintValidator<FutureDate, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isAfter(LocalDateTime.now());
    }
}