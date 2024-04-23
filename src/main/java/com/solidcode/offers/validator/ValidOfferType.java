package com.solidcode.offers.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({PARAMETER, METHOD, FIELD, LOCAL_VARIABLE})
@Retention(RUNTIME)
@Constraint(validatedBy = OfferTypeValidator.class)
@Documented
public @interface ValidOfferType {

    String message() default "Invalid offer type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
