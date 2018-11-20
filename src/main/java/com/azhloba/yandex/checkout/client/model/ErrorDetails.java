package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Aleh Zhloba
 */
public class ErrorDetails implements Identified, Serializable {

    private final String id;

    private final String type;

    private final String code;

    private final String description;

    private final Long retryAfter;

    private final String parameter;

    @JsonCreator
    public ErrorDetails(String type, String id, String code, String description, Long retryAfter, String parameter) {
        this.type = type;
        this.code = code;
        this.description = description;
        this.id = id;
        this.retryAfter = retryAfter;
        this.parameter = parameter;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Optional<String> getParameter() {
        return Optional.ofNullable(parameter);
    }

    public Optional<Long> getRetryAfter() {
        return Optional.ofNullable(retryAfter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorDetails that = (ErrorDetails) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(type, that.type) &&
                Objects.equals(code, that.code) &&
                Objects.equals(description, that.description) &&
                Objects.equals(retryAfter, that.retryAfter) &&
                Objects.equals(parameter, that.parameter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, code, description, retryAfter, parameter);
    }
}
