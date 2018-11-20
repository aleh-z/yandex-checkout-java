package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.money.MonetaryAmount;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Aleh Zhloba
 */
public class Refund implements Identified, Serializable {

    private final String id;

    private final String paymentId;

    private final RefundStatus status;

    private final Instant createdAt;

    private final MonetaryAmount amount;

    private final String receiptRegistration;

    private final String description;

    @JsonCreator
    public Refund(String id, String paymentId, RefundStatus status, Instant createdAt,
                  MonetaryAmount amount, String receiptRegistration, String description) {
        this.id = id;
        this.paymentId = paymentId;
        this.status = status;
        this.createdAt = createdAt;
        this.amount = amount;
        this.receiptRegistration = receiptRegistration;
        this.description = description;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public RefundStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }

    public Optional<String> getReceiptRegistration() {
        return Optional.ofNullable(receiptRegistration);
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Refund refund = (Refund) o;
        return Objects.equals(id, refund.id) &&
                Objects.equals(paymentId, refund.paymentId) &&
                status == refund.status &&
                Objects.equals(createdAt, refund.createdAt) &&
                Objects.equals(amount, refund.amount) &&
                Objects.equals(receiptRegistration, refund.receiptRegistration) &&
                Objects.equals(description, refund.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentId, status, createdAt, amount, receiptRegistration, description);
    }
}
