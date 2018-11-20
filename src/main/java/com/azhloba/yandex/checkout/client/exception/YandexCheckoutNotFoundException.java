package com.azhloba.yandex.checkout.client.exception;

/**
 * Exception thrown in case of 404 response code
 *
 * @author Aleh Zhloba
 */
public class YandexCheckoutNotFoundException extends YandexCheckoutException {

    public YandexCheckoutNotFoundException(String idempotenceKey) {
        super(idempotenceKey);
    }
}
