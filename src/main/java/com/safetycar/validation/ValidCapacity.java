package com.safetycar.validation;

import com.safetycar.validation.impl.ValidCapacityImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValidCapacityImpl.class})
public @interface ValidCapacity {

    String message() default "{safetycar.annotations.ValidCapacity.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
