package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Aleh Zhloba
 */
public class Card implements Serializable {

    private final String first6;

    private final String last4;

    private final String expiryYear;

    private final String expiryMonth;

    private final String cardType;

    @JsonCreator
    public Card(String first6, String last4, String expiryYear, String expiryMonth, String cardType) {
        this.first6 = first6;
        this.last4 = last4;
        this.expiryYear = expiryYear;
        this.expiryMonth = expiryMonth;
        this.cardType = cardType;
    }

    public String getFirst6() {
        return first6;
    }

    public String getLast4() {
        return last4;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public String getCardType() {
        return cardType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(first6, card.first6) &&
                Objects.equals(last4, card.last4) &&
                Objects.equals(expiryYear, card.expiryYear) &&
                Objects.equals(expiryMonth, card.expiryMonth) &&
                Objects.equals(cardType, card.cardType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first6, last4, expiryYear, expiryMonth, cardType);
    }
}
