package com.azhloba.yandex.checkout.client.exception;

import com.azhloba.yandex.checkout.client.model.ErrorDetails;

/**
 * Exception thrown in case of 400 response code
 *
 * @author Aleh Zhloba
 */
public class YandexCheckoutBadRequestException extends YandexCheckoutException {

    private final ErrorDetails errorDetails;

    public YandexCheckoutBadRequestException(String idempotenceKey, ErrorDetails errorDetails) {
        super(idempotenceKey);
        this.errorDetails = errorDetails;
    }

    public ErrorDetails getErrorDetails() {
        return errorDetails;
    }

}
