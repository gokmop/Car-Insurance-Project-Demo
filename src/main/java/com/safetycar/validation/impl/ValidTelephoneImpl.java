package com.safetycar.validation.impl;

import com.safetycar.validation.ValidTelephone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidTelephoneImpl implements ConstraintValidator<ValidTelephone, String> {

    private java.util.regex.Pattern pattern;

    @Override
    public void initialize(ValidTelephone constraintAnnotation) {
        pattern = Pattern.compile(constraintAnnotation.regexp());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return pattern.matcher(value).matches();
    }

}
