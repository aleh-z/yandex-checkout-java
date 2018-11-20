package com.azhloba.yandex.checkout.client.request;

import com.azhloba.yandex.checkout.client.model.Airline;
import com.azhloba.yandex.checkout.client.model.Payment;
import com.azhloba.yandex.checkout.client.model.Receipt;

import javax.money.MonetaryAmount;

/**
 * @author Aleh Zhloba
 */
public class CapturePaymentRequest implements YandexCheckoutRequest {

    public final MonetaryAmount amount;

    public final Receipt receipt;

    public final Airline airline;

    public CapturePaymentRequest(MonetaryAmount amount, Receipt receipt, Airline airline) {
        this.amount = amount;
        this.receipt = receipt;
        this.airline = airline;
    }


    public static final class Builder extends YandexCheckoutRequestBuilder<Payment> {
        private MonetaryAmount amount;
        private Receipt receipt;
        private Airline airline;

        public Builder(RequestExecutor<Payment> executor) {
            super(executor);
        }

        public Builder withAmount(MonetaryAmount amount) {
            this.amount = amount;
            return this;
        }

        public Builder withReceipt(Receipt receipt) {
            this.receipt = receipt;
            return this;
        }

        public Builder withAirline(Airline airline) {
            this.airline = airline;
            return this;
        }

        @Override
        protected CapturePaymentRequest build() {
            return new CapturePaymentRequest(amount, receipt, airline);
        }
    }
}
