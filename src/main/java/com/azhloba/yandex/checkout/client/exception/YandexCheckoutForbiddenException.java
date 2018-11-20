package com.azhloba.yandex.checkout.client.exception;

/**
 * Exception thrown in case of 403 response code
 *
 * @author Aleh Zhloba
 */
public class YandexCheckoutForbiddenException extends YandexCheckoutException {

    public YandexCheckoutForbiddenException(String idempotenceKey) {
        super(idempotenceKey);
    }
}
