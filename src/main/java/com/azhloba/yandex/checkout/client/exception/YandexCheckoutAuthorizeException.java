package com.azhloba.yandex.checkout.client.exception;

/**
 * Exception thrown in case of 401 response code
 *
 * @author Aleh Zhloba
 */
public class YandexCheckoutAuthorizeException extends YandexCheckoutException {

    public YandexCheckoutAuthorizeException(String idempotenceKey) {
        super(idempotenceKey);
    }
}
