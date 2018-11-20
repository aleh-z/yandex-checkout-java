package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Aleh Zhloba
 */
public class Recipient implements Serializable {

    private final String gatewayId;

    @JsonCreator
    public Recipient(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getGatewayId() {
        return gatewayId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipient recipient = (Recipient) o;
        return Objects.equals(gatewayId, recipient.gatewayId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gatewayId);
    }
}
