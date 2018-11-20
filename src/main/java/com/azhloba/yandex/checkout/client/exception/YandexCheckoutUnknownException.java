package com.azhloba.yandex.checkout.client.exception;

import com.azhloba.yandex.checkout.client.model.ErrorDetails;

import java.util.Optional;

/**
 * Exception thrown in case of unexpected response error code
 *
 * @author Aleh Zhloba
 */
public class YandexCheckoutUnknownException extends YandexCheckoutException {

    private final int statusCode;

    private final ErrorDetails errorDetails;

    public YandexCheckoutUnknownException(String idempotenceKey, int statusCode, ErrorDetails errorDetails) {
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
