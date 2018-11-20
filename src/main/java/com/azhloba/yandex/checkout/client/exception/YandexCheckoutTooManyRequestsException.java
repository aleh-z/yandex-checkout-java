package com.azhloba.yandex.checkout.client.exception;

/**
 * Exception thrown in case of 429 response code
 *
 * @author Aleh Zhloba
 */
public class YandexCheckoutTooManyRequestsException extends YandexCheckoutException {

    public YandexCheckoutTooManyRequestsException(String idempotenceKey) {
        super(idempotenceKey);
    }
}
