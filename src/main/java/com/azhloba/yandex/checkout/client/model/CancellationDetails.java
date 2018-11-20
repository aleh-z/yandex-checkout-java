package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Aleh Zhloba
 */
public class CancellationDetails implements Serializable {

    private final String party;

    private final String reason;

    @JsonCreator
    public CancellationDetails(String party, String reason) {
        this.party = party;
        this.reason = reason;
    }

    public String getParty() {
        return party;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CancellationDetails that = (CancellationDetails) o;
        return Objects.equals(party, that.party) &&
                Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(party, reason);
    }
}
