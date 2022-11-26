package com.safetycar.validation.impl;

import com.safetycar.enums.AllowedContent;
import com.safetycar.validation.ValidFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidFileImpl implements ConstraintValidator<ValidFile, MultipartFile> {

    private Set<String> allowedContentTypes;

    @Override
    public void initialize(ValidFile constraintAnnotation) {
        allowedContentTypes = Arrays.stream(constraintAnnotation.value())
                .map(AllowedContent::toString).collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null) return false;
        boolean notEmpty = !file.isEmpty();
        boolean allowedContent = allowedContentTypes.contains(file.getContentType());
        return notEmpty && allowedContent;
    }
}
