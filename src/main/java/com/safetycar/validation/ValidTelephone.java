package com.safetycar.validation;

import com.safetycar.validation.impl.ValidTelephoneImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {ValidTelephoneImpl.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface ValidTelephone {

    String message() default "{safetycar.annotations.ValidTelephone.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regexp() default "";

}
