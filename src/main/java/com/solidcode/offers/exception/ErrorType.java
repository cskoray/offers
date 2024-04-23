package com.solidcode.offers.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ErrorType {

    OFFER_NOT_FOUND("10001", "Offer cannot be found.", BAD_REQUEST),
    DUPLICATE_OFFER("10002", "Offer exists.", BAD_REQUEST),
    INVALID_FIELD("10003", "Invalid field.", BAD_REQUEST),
    MERCHANT_NOT_FOUND("10004", "Merchant cannot be found.", BAD_REQUEST);

    private String code;
    private String message;
    private HttpStatus httpStatus;
}
