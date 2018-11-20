package com.azhloba.yandex.checkout.client.exception;

import java.io.IOException;

/**
 * Wrapper for all IOException and UncheckedIOException thrown during client method call
 *
 * @author Aleh Zhloba
 */
public class YandexCheckoutIOException extends YandexCheckoutException {

    public YandexCheckoutIOException(String idempotenceKey, IOException e) {
        super(idempotenceKey);
        this.initCause(e);
    }

}
