package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Aleh Zhloba
 */
public enum PaymentStatus {

    PENDING, WAITING_FOR_CAPTURE, SUCCEEDED, CANCELED;

    @JsonCreator
    public static PaymentStatus fromString(String status) {
        return status == null
                ? null
                : PaymentStatus.valueOf(status.toUpperCase());
    }

    @JsonValue
    public String getStatus() {
        return this.name().toLowerCase();
    }
}
