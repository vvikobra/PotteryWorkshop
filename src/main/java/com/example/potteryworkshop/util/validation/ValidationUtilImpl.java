package com.example.potteryworkshop.util.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ValidationUtilImpl implements ValidationUtil {

    private final Validator validator;

    public ValidationUtilImpl(Validator validator) {
        this.validator = validator;
    }

    @Override
    public <E> boolean isValid(E object) {
        return this.validator.validate(object).isEmpty();
    }

    @Override
    public <E> Set<ConstraintViolation<E>> violations(E object) {
        return this.validator.validate(object);
    }
}
