package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Aleh Zhloba
 */
public class Confirmation implements Serializable {

    private final String type;

    private final Boolean enforce;

    private final String returnUrl;

    private final String confirmationUrl;

    @JsonCreator
    public Confirmation(String type, Boolean enforce, String returnUrl, String confirmationUrl) {
        this.type = type;
        this.enforce = enforce;
        this.returnUrl = returnUrl;
        this.confirmationUrl = confirmationUrl;
    }

    public String getType() {
        return type;
    }

    public Optional<Boolean> getEnforce() {
        return Optional.ofNullable(enforce);
    }

    public Optional<String> getReturnUrl() {
        return Optional.ofNullable(returnUrl);
    }

    public String getConfirmationUrl() {
        return confirmationUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Confirmation that = (Confirmation) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(enforce, that.enforce) &&
                Objects.equals(returnUrl, that.returnUrl) &&
                Objects.equals(confirmationUrl, that.confirmationUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, enforce, returnUrl, confirmationUrl);
    }
}
