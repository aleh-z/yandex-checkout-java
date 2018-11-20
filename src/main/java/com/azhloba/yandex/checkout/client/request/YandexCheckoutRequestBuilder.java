package com.azhloba.yandex.checkout.client.request;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

/**
 * @author Aleh Zhloba
 */
public abstract class YandexCheckoutRequestBuilder<Rs> {

    protected final RequestExecutor<Rs> executor;

    public YandexCheckoutRequestBuilder(RequestExecutor<Rs> executor) {
        this.executor = executor;
    }

    public CompletionStage<Rs> execute() {
        return execute(null);
    }

    public CompletionStage<Rs> execute(String idempotenceKey) {
        String key = idempotenceKey == null ? UUID.randomUUID().toString() : idempotenceKey;

        return executor.execute(build(), key);
    }


    abstract protected YandexCheckoutRequest build();


    @FunctionalInterface
    public interface RequestExecutor<Rs> {
        CompletionStage<Rs> execute(YandexCheckoutRequest request, String idempotenceKey);
    }

}
