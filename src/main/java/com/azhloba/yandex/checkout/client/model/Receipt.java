package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Aleh Zhloba
 */
public class Receipt implements Serializable {

    private final List<Item> items;

    private final String taxSystemCode;

    private final String phone;

    private final String email;

    @JsonCreator
    public Receipt(List<Item> items, String taxSystemCode, String phone, String email) {
        this.items = items;
        this.taxSystemCode = taxSystemCode;
        this.phone = phone;
        this.email = email;
    }

    public List<Item> getItems() {
        return items;
    }

    public Optional<String> getTaxSystemCode() {
        return Optional.ofNullable(taxSystemCode);
    }

    public Optional<String> getPhone() {
        return Optional.ofNullable(phone);
    }

    public Optional<String> getEmail() {
        return Optional.ofNullable(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return Objects.equals(items, receipt.items) &&
                Objects.equals(taxSystemCode, receipt.taxSystemCode) &&
                Objects.equals(phone, receipt.phone) &&
                Objects.equals(email, receipt.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items, taxSystemCode, phone, email);
    }
}
