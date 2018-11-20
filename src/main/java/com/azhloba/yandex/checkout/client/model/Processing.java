package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Aleh Zhloba
 */
public class Processing implements Serializable {

    private final String description;

    private final Integer retryAfter;

    @JsonCreator
    public Processing(String description, Integer retryAfter) {
        this.description = description;
        this.retryAfter = retryAfter;
    }

    public String getDescription() {
        return description;
    }

    public Integer getRetryAfter() {
        return retryAfter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Processing that = (Processing) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(retryAfter, that.retryAfter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, retryAfter);
    }
}
