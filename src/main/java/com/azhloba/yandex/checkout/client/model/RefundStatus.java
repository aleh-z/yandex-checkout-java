package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Aleh Zhloba
 */
public enum RefundStatus {

    SUCCEEDED, CANCELED;

    @JsonCreator
    public static RefundStatus fromString(String status) {
        return status == null
                ? null
                : RefundStatus.valueOf(status.toUpperCase());
    }

    @JsonValue
    public String getStatus() {
        return this.name().toLowerCase();
    }

}
