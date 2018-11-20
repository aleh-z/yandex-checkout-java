package com.azhloba.yandex.checkout.client.exception;

import com.azhloba.yandex.checkout.client.model.ErrorDetails;

import java.util.Optional;

/**
 * Exception thrown in case of 5xx response codes
 *
 * @author Aleh Zhloba
 */
public class YandexCheckoutServerException extends YandexCheckoutException {

    private final int statusCode;

    private final ErrorDetails errorDetails;

    public YandexCheckoutServerException(String idempotenceKey, int statusCode, ErrorDetails errorDetails) {
        super(idempotenceKey);
        this.statusCode = statusCode;
        this.errorDetails = errorDetails;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Optional<ErrorDetails> getErrorDetails() {
        return Optional.ofNullable(errorDetails);
    }
}
