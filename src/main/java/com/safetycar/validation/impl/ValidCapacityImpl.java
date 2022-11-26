package com.safetycar.validation.impl;

import com.safetycar.validation.ValidCapacity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidCapacityImpl implements ConstraintValidator<ValidCapacity, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null) return  false;
        try {
            int capacity = Integer.parseInt(value);
            return capacity >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
