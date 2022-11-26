package com.safetycar.validation.impl;


import com.safetycar.validation.MaxAge;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ValidMaxAgeLocalDateImpl implements ConstraintValidator<MaxAge, LocalDate> {

    private long maxValue;

    @Override
    public void initialize(MaxAge maxValue) {
        this.maxValue = maxValue.value().getAge();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return LocalDate.now().minusYears(maxValue).isBefore(value);

    }
}
