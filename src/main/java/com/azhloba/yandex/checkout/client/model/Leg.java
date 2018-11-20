package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Aleh Zhloba
 */
public class Leg implements Serializable {

    private final String departureAirport;

    private final String destinationAirport;

    private final LocalDate departureDate;

    @JsonCreator
    public Leg(String departureAirport, String destinationAirport, LocalDate departureDate) {
        this.departureAirport = departureAirport;
        this.destinationAirport = destinationAirport;
        this.departureDate = departureDate;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Leg leg = (Leg) o;
        return Objects.equals(departureAirport, leg.departureAirport) &&
                Objects.equals(destinationAirport, leg.destinationAirport) &&
                Objects.equals(departureDate, leg.departureDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departureAirport, destinationAirport, departureDate);
    }
}
