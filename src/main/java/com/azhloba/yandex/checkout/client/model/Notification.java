package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Aleh Zhloba
 */
public class Notification implements Serializable {

    protected final String type;

    protected final String event;

    protected final Identified object;

    @JsonCreator
    public Notification(String type, String event, Identified object) {
        this.type = type;
        this.event = event;
        this.object = object;
    }

    public String getType() {
        return type;
    }

    public String getEvent() {
        return event;
    }

    public Identified getObject() {
        return object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(event, that.event) &&
                Objects.equals(object, that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, event, object);
    }
}
