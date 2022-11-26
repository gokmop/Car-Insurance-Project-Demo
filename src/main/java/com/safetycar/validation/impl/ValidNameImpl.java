package com.safetycar.validation.impl;

import com.safetycar.validation.ValidName;
import com.safetycar.validation.ValidTelephone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidNameImpl implements ConstraintValidator<ValidName, String> {

    private Pattern pattern;

    @Override
    public void initialize(ValidName constraintAnnotation) {
        pattern = Pattern.compile(constraintAnnotation.regexp());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return pattern.matcher(value).matches();
    }

}
