package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.money.MonetaryAmount;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Aleh Zhloba
 */
public class VatData implements Serializable {

    private final String type;

    private final String rate;

    private final MonetaryAmount amount;

    @JsonCreator
    public VatData(String type, String rate, MonetaryAmount amount) {
        this.type = type;
        this.rate = rate;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public String getRate() {
        return rate;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VatData vatData = (VatData) o;
        return Objects.equals(type, vatData.type) &&
                Objects.equals(rate, vatData.rate) &&
                Objects.equals(amount, vatData.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, rate, amount);
    }
}
