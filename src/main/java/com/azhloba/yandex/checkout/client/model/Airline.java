package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Aleh Zhloba
 */
public class Airline implements Serializable {

    private final String bookingReference;

    private final String ticketNumber;

    private final List<Passenger> passengers;

    private final List<Leg> legs;

    @JsonCreator
    public Airline(String bookingReference, String ticketNumber, List<Passenger> passengers, List<Leg> legs) {
        this.bookingReference = bookingReference;
        this.ticketNumber = ticketNumber;
        this.passengers = passengers;
        this.legs = legs;
    }

    public Optional<String> getBookingReference() {
        return Optional.ofNullable(bookingReference);
    }

    public Optional<String> getTicketNumber() {
        return Optional.ofNullable(ticketNumber);
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airline airline = (Airline) o;
        return Objects.equals(bookingReference, airline.bookingReference) &&
                Objects.equals(ticketNumber, airline.ticketNumber) &&
                Objects.equals(passengers, airline.passengers) &&
                Objects.equals(legs, airline.legs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingReference, ticketNumber, passengers, legs);
    }
}
