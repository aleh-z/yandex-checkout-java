package com.azhloba.yandex.checkout.client.request;

import com.azhloba.yandex.checkout.client.model.Receipt;
import com.azhloba.yandex.checkout.client.model.Refund;

import javax.money.MonetaryAmount;

/**
 * @author Aleh Zhloba
 */
public class CreateRefundRequest implements YandexCheckoutRequest {

    public final String paymentId;

    public final MonetaryAmount amount;

    public final String description;

    public final Receipt receipt;

    public CreateRefundRequest(String paymentId, MonetaryAmount amount, String description, Receipt receipt) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.description = description;
        this.receipt = receipt;
    }


    public static final class Builder extends YandexCheckoutRequestBuilder<Refund>{
        private final String paymentId;
        private MonetaryAmount amount;
        private String description;
        private Receipt receipt;

        public Builder(RequestExecutor<Refund> executor, String paymentId) {
            super(executor);
            this.paymentId = paymentId;
        }

        public Builder withAmount(MonetaryAmount amount) {
            this.amount = amount;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withReceipt(Receipt receipt) {
            this.receipt = receipt;
            return this;
        }

        @Override
        protected CreateRefundRequest build() {
            return new CreateRefundRequest(paymentId, amount, description, receipt);
        }
    }
}
