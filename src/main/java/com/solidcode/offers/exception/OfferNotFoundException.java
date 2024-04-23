package com.solidcode.offers.exception;


import lombok.Getter;

@Getter
public class OfferNotFoundException extends RuntimeException {

    private ErrorType errorType;
    private String field;

    public OfferNotFoundException(ErrorType errorType, String field) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.field = field;
    }
}
