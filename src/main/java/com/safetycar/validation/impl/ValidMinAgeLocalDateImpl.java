package com.safetycar.validation.impl;

import com.safetycar.validation.MinAge;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;


public class ValidMinAgeLocalDateImpl implements ConstraintValidator<MinAge, LocalDate> {

    private long minValue;

    @Override
    public void initialize(MinAge minValue) {
        this.minValue = minValue.value().getAge();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        LocalDate years = LocalDate.now().minusYears(minValue);
        return years.isAfter(value) || years.isEqual(value);
    }
}
