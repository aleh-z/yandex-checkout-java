package com.azhloba.yandex.checkout.client.exception;

import java.util.Optional;

/**
 * Parent exception class. Holds optional request idempotence key
 *
 * @author Aleh Zhloba
 */
public abstract class YandexCheckoutException extends RuntimeException {

    private final String idempotenceKey;

    public YandexCheckoutException(String idempotenceKey) {
        this.idempotenceKey = idempotenceKey;
    }

    public Optional<String> getIdempotenceKey() {
        return Optional.ofNullable(idempotenceKey);
    }
}
