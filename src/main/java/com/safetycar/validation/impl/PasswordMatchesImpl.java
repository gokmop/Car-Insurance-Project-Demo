package com.safetycar.validation.impl;

import com.safetycar.validation.PasswordMatches;
import com.safetycar.web.dto.user.CreateUserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesImpl implements ConstraintValidator<PasswordMatches, CreateUserDto> {

    @Override
    public boolean isValid(final CreateUserDto dto, final ConstraintValidatorContext context) {
        if(dto == null) return false;
        return dto.getPassword().equals(dto.getConfirmationPassword());
    }

}
