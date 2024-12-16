package com.example.potteryworkshop.util.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class PastDateValidator implements ConstraintValidator<PastDate, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isBefore(LocalDateTime.now());
    }
}
