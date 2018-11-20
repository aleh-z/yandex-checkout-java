package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.money.MonetaryAmount;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Aleh Zhloba
 */
public class Item implements Serializable {

    private final String description;

    private final String quantity;

    private final MonetaryAmount amount;

    private final Integer vatCode;

    @JsonCreator
    public Item(String description, String quantity, MonetaryAmount amount, Integer vatCode) {
        this.description = description;
        this.quantity = quantity;
        this.amount = amount;
        this.vatCode = vatCode;
    }

    public String getDescription() {
        return description;
    }

    public String getQuantity() {
        return quantity;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }

    public Integer getVatCode() {
        return vatCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(description, item.description) &&
                Objects.equals(quantity, item.quantity) &&
                Objects.equals(amount, item.amount) &&
                Objects.equals(vatCode, item.vatCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, quantity, amount, vatCode);
    }
}
