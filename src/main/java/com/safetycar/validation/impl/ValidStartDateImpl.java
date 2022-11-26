package com.safetycar.validation.impl;


import com.safetycar.validation.ValidStartDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ValidStartDateImpl implements ConstraintValidator<ValidStartDate, LocalDate> {

    private long maxDaysAhead;

    @Override
    public void initialize(ValidStartDate maxValue) {
        this.maxDaysAhead = maxValue.value();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        LocalDate nowDate = LocalDate.now();
        LocalDate nowPlusDaysAhead = nowDate.plusDays(maxDaysAhead);
        boolean futureMax = value.isBefore(nowPlusDaysAhead)
                || value.isEqual(nowPlusDaysAhead);
        boolean now = value.isAfter(nowDate);
        return futureMax && now;

    }
}
