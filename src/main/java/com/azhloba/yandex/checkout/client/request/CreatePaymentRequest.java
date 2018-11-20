package com.azhloba.yandex.checkout.client.request;

import com.azhloba.yandex.checkout.client.model.Payment;

import javax.money.MonetaryAmount;

/**
 * @author Aleh Zhloba
 */
public class CreatePaymentRequest implements YandexCheckoutRequest {

    public final MonetaryAmount amount;

    public final String description;

    public final boolean capture;

    public CreatePaymentRequest(MonetaryAmount amount, String description, boolean capture) {
        this.amount = amount;
        this.description = description;
        this.capture = capture;
    }


    public static final class Builder extends YandexCheckoutRequestBuilder<Payment>{
        private final MonetaryAmount amount;
        private String description;
        private boolean capture;

        public Builder(RequestExecutor<Payment> executor, MonetaryAmount amount) {
            super(executor);
            this.amount = amount;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withCapture(boolean capture) {
            this.capture = capture;
            return this;
        }

        @Override
        protected CreatePaymentRequest build() {
            return new CreatePaymentRequest(amount, description, capture);
        }
    }
}
