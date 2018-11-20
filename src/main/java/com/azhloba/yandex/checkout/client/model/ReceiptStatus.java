package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Aleh Zhloba
 */
public enum ReceiptStatus {

    PENDING, SUCCEEDED, CANCELED;

    @JsonCreator
    public static ReceiptStatus fromString(String status) {
        return status == null
                ? null
                : ReceiptStatus.valueOf(status.toUpperCase());
    }

    @JsonValue
    public String getStatus() {
        return this.name().toLowerCase();
    }

}
