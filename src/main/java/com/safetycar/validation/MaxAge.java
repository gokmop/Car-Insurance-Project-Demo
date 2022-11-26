package com.safetycar.validation;

import com.safetycar.enums.LegalAges;
import com.safetycar.validation.impl.ValidMaxAgeLocalDateImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValidMaxAgeLocalDateImpl.class})
public @interface MaxAge {

    LegalAges value();

    String message() default "{beertag.annotations.MaxAge.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
