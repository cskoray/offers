package com.solidcode.offers.validator;

import com.solidcode.offers.util.OfferType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OfferTypeValidator implements ConstraintValidator<ValidOfferType, String> {

    @Override
    public void initialize(ValidOfferType constraintAnnotation) {
    }

    @Override
    public boolean isValid(String type, ConstraintValidatorContext context) {
        return (validateOfferType(type));
    }

    private boolean validateOfferType(String type) {
        return OfferType.contains(type);
    }
}