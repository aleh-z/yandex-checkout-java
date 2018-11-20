package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Aleh Zhloba
 */
public class PaymentMethod implements Identified, Serializable {

    private final String type;

    private final String id;

    private final boolean saved;

    private final String title;

    private final String accountNumber;

    private final String login;

    private final Card card;

    private final String phone;

    private final String paymentPurpose;

    private final VatData vatData;

    private final PayerBankDetails payerBankDetails;

    @JsonCreator
    public PaymentMethod(String type, String id, boolean saved, String title, String accountNumber, String login,
                         Card card, String phone, String paymentPurpose, VatData vatData, PayerBankDetails payerBankDetails) {
        this.type = type;
        this.id = id;
        this.saved = saved;
        this.title = title;
        this.accountNumber = accountNumber;
        this.login = login;
        this.card = card;
        this.phone = phone;
        this.paymentPurpose = paymentPurpose;
        this.vatData = vatData;
        this.payerBankDetails = payerBankDetails;
    }

    public String getType() {
        return type;
    }

    @Override
    public String getId() {
        return id;
    }

    public boolean getSaved() {
        return saved;
    }

    public String getLogin() {
        return login;
    }

    public Optional<String> getTitle() {
        return Optional.ofNullable(title);
    }

    public Optional<String> getAccountNumber() {
        return Optional.ofNullable(accountNumber);
    }

    public Optional<Card> getCard() {
        return Optional.ofNullable(card);
    }

    public Optional<String> getPhone() {
        return Optional.ofNullable(phone);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentMethod that = (PaymentMethod) o;
        return saved == that.saved &&
                Objects.equals(type, that.type) &&
                Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(login, that.login) &&
                Objects.equals(card, that.card) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(paymentPurpose, that.paymentPurpose) &&
                Objects.equals(vatData, that.vatData) &&
                Objects.equals(payerBankDetails, that.payerBankDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, id, saved, title, accountNumber, login, card, phone, paymentPurpose, vatData, payerBankDetails);
    }
}
