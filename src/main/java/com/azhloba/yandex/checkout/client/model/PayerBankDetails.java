package com.azhloba.yandex.checkout.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Aleh Zhloba
 */
public class PayerBankDetails implements Serializable {

    private final String fullName;

    private final String shortName;

    private final String address;

    private final String inn;

    private final String kpp;

    private final String bankName;

    private final String bankBranch;

    private final String bankBik;

    private final String account;

    @JsonCreator
    public PayerBankDetails(String fullName, String shortName, String address, String inn, String kpp, String bankName,
                            String bankBranch, String bankBik, String account) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.address = address;
        this.inn = inn;
        this.kpp = kpp;
        this.bankName = bankName;
        this.bankBranch = bankBranch;
        this.bankBik = bankBik;
        this.account = account;
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getAddress() {
        return address;
    }

    public String getInn() {
        return inn;
    }

    public String getKpp() {
        return kpp;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public String getBankBik() {
        return bankBik;
    }

    public String getAccount() {
        return account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayerBankDetails that = (PayerBankDetails) o;
        return Objects.equals(fullName, that.fullName) &&
                Objects.equals(shortName, that.shortName) &&
                Objects.equals(address, that.address) &&
                Objects.equals(inn, that.inn) &&
                Objects.equals(kpp, that.kpp) &&
                Objects.equals(bankName, that.bankName) &&
                Objects.equals(bankBranch, that.bankBranch) &&
                Objects.equals(bankBik, that.bankBik) &&
                Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, shortName, address, inn, kpp, bankName, bankBranch, bankBik, account);
    }
}
