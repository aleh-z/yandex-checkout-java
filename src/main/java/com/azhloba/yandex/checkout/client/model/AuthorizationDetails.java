package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Aleh Zhloba
 */
public class AuthorizationDetails implements Serializable {

    private final String rrn;

    private final String authCode;

    @JsonCreator
    public AuthorizationDetails(String rrn, String authCode) {
        this.rrn = rrn;
        this.authCode = authCode;
    }

    public Optional<String> getRrn() {
        return Optional.ofNullable(rrn);
    }

    public Optional<String> getAuthCode() {
        return Optional.ofNullable(authCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorizationDetails that = (AuthorizationDetails) o;
        return Objects.equals(rrn, that.rrn) &&
                Objects.equals(authCode, that.authCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rrn, authCode);
    }
}
