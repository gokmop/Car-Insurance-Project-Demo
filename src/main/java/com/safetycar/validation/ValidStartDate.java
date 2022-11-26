package com.safetycar.validation;


import com.safetycar.validation.impl.ValidStartDateImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValidStartDateImpl.class})
public @interface ValidStartDate {

    long value();

    String message() default "{safetycar.annotations.ValidStartDate.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
