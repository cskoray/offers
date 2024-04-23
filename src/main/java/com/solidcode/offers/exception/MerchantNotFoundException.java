package com.solidcode.offers.exception;


import lombok.Getter;

@Getter
public class MerchantNotFoundException extends RuntimeException {

    private ErrorType errorType;
    private String field;

    public MerchantNotFoundException(ErrorType errorType, String field) {
        super(errorType.getMessage());
        this.errorType = errorType;
        this.field = field;
    }
}
