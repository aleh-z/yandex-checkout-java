package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.money.MonetaryAmount;
import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Aleh Zhloba
 */
public class Payment implements Identified, Serializable {

    private final String id;

    private final PaymentStatus status;

    private final MonetaryAmount amount;

    private final String description;

    private final Recipient recipient;

    private final PaymentMethod paymentMethod;

    private final Instant capturedAt;

    private final Instant createdAt;

    private final Instant expiresAt;

    private final Confirmation confirmation;

    private final boolean test;

    private final MonetaryAmount refundedAmount;

    private final boolean paid;

    private final ReceiptStatus receiptRegistration;

    private final Map<String, String> metadata;

    private final CancellationDetails cancellationDetails;

    private final AuthorizationDetails authorizationDetails;

    @JsonCreator
    public Payment(String id, PaymentStatus status, MonetaryAmount amount, String description, Recipient recipient,
                   PaymentMethod paymentMethod, Instant capturedAt, Instant createdAt, Instant expiresAt,
                   Confirmation confirmation,  boolean test, MonetaryAmount refundedAmount, boolean paid,
                   ReceiptStatus receiptRegistration, Map<String, String> metadata, CancellationDetails cancellationDetails,
                   AuthorizationDetails authorizationDetails) {
        this.id = id;
        this.status = status;
        this.amount = amount;
        this.description = description;
        this.recipient = recipient;
        this.paymentMethod = paymentMethod;
        this.capturedAt = capturedAt;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.confirmation = confirmation;
        this.test = test;
        this.refundedAmount = refundedAmount;
        this.paid = paid;
        this.receiptRegistration = receiptRegistration;
        this.metadata = metadata;
        this.cancellationDetails = cancellationDetails;
        this.authorizationDetails = authorizationDetails;
    }

    @Override
    public String getId() {
        return id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }

    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<Recipient> getRecipient() {
        return Optional.ofNullable(recipient);
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public Optional<Instant> getCapturedAt() {
        return Optional.ofNullable(capturedAt);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Optional<Instant> getExpiresAt() {
        return Optional.ofNullable(expiresAt);
    }

    public Optional<Confirmation> getConfirmation() {
        return Optional.ofNullable(confirmation);
    }

    public boolean getTest() {
        return test;
    }

    public Optional<MonetaryAmount> getRefundedAmount() {
        return Optional.ofNullable(refundedAmount);
    }

    public boolean getPaid() {
        return paid;
    }

    public Optional<ReceiptStatus> getReceiptRegistration() {
        return Optional.ofNullable(receiptRegistration);
    }

    public Optional<Map<String, String>> getMetadata() {
        return Optional.ofNullable(metadata);
    }

    public Optional<CancellationDetails> getCancellationDetails() {
        return Optional.ofNullable(cancellationDetails);
    }

    public Optional<AuthorizationDetails> getAuthorizationDetails() {
        return Optional.ofNullable(authorizationDetails);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return test == payment.test &&
                paid == payment.paid &&
                Objects.equals(id, payment.id) &&
                status == payment.status &&
                Objects.equals(amount, payment.amount) &&
                Objects.equals(description, payment.description) &&
                Objects.equals(recipient, payment.recipient) &&
                Objects.equals(paymentMethod, payment.paymentMethod) &&
                Objects.equals(capturedAt, payment.capturedAt) &&
                Objects.equals(createdAt, payment.createdAt) &&
                Objects.equals(expiresAt, payment.expiresAt) &&
                Objects.equals(confirmation, payment.confirmation) &&
                Objects.equals(refundedAmount, payment.refundedAmount) &&
                receiptRegistration == payment.receiptRegistration &&
                Objects.equals(metadata, payment.metadata) &&
                Objects.equals(cancellationDetails, payment.cancellationDetails) &&
                Objects.equals(authorizationDetails, payment.authorizationDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, amount, description, recipient, paymentMethod, capturedAt, createdAt, expiresAt, confirmation, test, refundedAmount, paid, receiptRegistration, metadata, cancellationDetails, authorizationDetails);
    }
}
