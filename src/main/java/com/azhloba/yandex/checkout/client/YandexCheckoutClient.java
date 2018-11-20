package com.azhloba.yandex.checkout.client;

import com.azhloba.yandex.checkout.client.exception.*;
import com.azhloba.yandex.checkout.client.json.DefaultJsonMapperImpl;
import com.azhloba.yandex.checkout.client.json.JsonMapper;
import com.azhloba.yandex.checkout.client.model.ErrorDetails;
import com.azhloba.yandex.checkout.client.model.Notification;
import com.azhloba.yandex.checkout.client.model.Payment;
import com.azhloba.yandex.checkout.client.model.Refund;
import com.azhloba.yandex.checkout.client.request.CapturePaymentRequest;
import com.azhloba.yandex.checkout.client.request.CreatePaymentRequest;
import com.azhloba.yandex.checkout.client.request.CreateRefundRequest;
import com.azhloba.yandex.checkout.client.request.YandexCheckoutRequest;
import com.nurkiewicz.asyncretry.AsyncRetryExecutor;
import com.nurkiewicz.asyncretry.RetryExecutor;
import org.asynchttpclient.*;

import javax.money.MonetaryAmount;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.concurrent.*;

import static com.azhloba.yandex.checkout.client.Constants.*;
import static java.util.Objects.requireNonNull;
import static org.asynchttpclient.Dsl.config;
import static org.asynchttpclient.Dsl.request;
import static org.asynchttpclient.util.HttpConstants.Methods.GET;
import static org.asynchttpclient.util.HttpConstants.Methods.POST;


/**
 * Yandex.Checkout API client
 *
 * @author Aleh Zhloba
 */
public class YandexCheckoutClient {

    protected final AsyncHttpClient httpClient;
 
    protected final RetryExecutor executor;

    protected final JsonMapper mapper;

    protected final String apiUrl;


    protected YandexCheckoutClient(AsyncHttpClient httpClient, RetryExecutor retryExecutor, JsonMapper mapper, String apiUrl) {
        this.httpClient = httpClient;
        this.executor = retryExecutor;
        this.mapper = mapper;
        this.apiUrl = apiUrl;
    }

    public static YandexCheckoutClient.Builder builder(String storeId, String secretKey) {
        requireNonNull(storeId);
        requireNonNull(secretKey);

        return new YandexCheckoutClient.Builder(storeId, secretKey);
    }

    public CompletionStage<Payment> getPayment(String paymentId) {
        requireNonNull(paymentId);
        
        return get("payments/" + paymentId, Payment.class);
    }

    public CompletionStage<Payment> createPayment(MonetaryAmount amount) {
        return createPayment(amount, null);
    }

    public CompletionStage<Payment> createPayment(MonetaryAmount amount, String idempotenceKey) {
        return createPaymentBuilder(amount).execute(idempotenceKey);
    }

    public CreatePaymentRequest.Builder createPaymentBuilder(MonetaryAmount amount) {
        requireNonNull(amount);

        return new CreatePaymentRequest.Builder(
                (rqModel, idKey) -> post("payments", idKey, rqModel, Payment.class), amount);
    }

    public CompletionStage<Payment> capturePayment(String paymentId) {
        return capturePayment(paymentId, null);
    }

    public CompletionStage<Payment> capturePayment(String paymentId, String idempotenceKey) {
        return capturePaymentBuilder(paymentId).execute(idempotenceKey);
    }

    public CapturePaymentRequest.Builder capturePaymentBuilder(String paymentId) {
        requireNonNull(paymentId);

        return new CapturePaymentRequest.Builder(
                (rqModel, idKey) -> post("payments/" + paymentId + "/capture", idKey, rqModel, Payment.class));
    }

    public CompletionStage<Payment> cancelPayment(String paymentId) {
        return cancelPayment(paymentId, null);
    }

    public CompletionStage<Payment> cancelPayment(String paymentId, String idempotenceKey) {
        requireNonNull(paymentId);

        return post("payments/" + paymentId + "/cancel", idempotenceKey, null, Payment.class);
    }


    public CompletionStage<Refund> getRefund(String refundId) {
        requireNonNull(refundId);

        return get("refunds/" + refundId, Refund.class);
    }

    public CompletionStage<Refund> createRefund(String paymentId) {
        return createRefund(paymentId, null);
    }

    public CompletionStage<Refund> createRefund(String paymentId, String idempotenceKey) {
        return createRefundBuilder(paymentId).execute(idempotenceKey);
    }

    public CreateRefundRequest.Builder createRefundBuilder(String paymentId) {
        requireNonNull(paymentId);

        return new CreateRefundRequest.Builder(
                (rqModel, idKey) -> this.post("refunds", idKey, rqModel, Refund.class), paymentId);
    }

    /**
     * Deserialize {@link Notification} from raw json response string
     */
    public Notification readNotification(String jsonString) {
        return mapper.read(jsonString, Notification.class);
    }

    /**
     * Construct and execute POST request
     */
    protected <Rs> CompletionStage<Rs> post(String endpoint, String idempotenceKey,
                                            YandexCheckoutRequest rqModel, Class<Rs> resultType) {
        RequestBuilder rqBuilder = request(POST, apiUrl + endpoint)
                .addHeader(IDEMPOTENCE_KEY_HEADER, idempotenceKey);

        if (rqModel != null) {
            rqBuilder.setBody(mapper.write(rqModel));
        }

        return execute(rqBuilder.build(), idempotenceKey, resultType);
    }

    /**
     * Construct and execute GET request
     */
    protected <Rs> CompletionStage<Rs> get(String endpoint, Class<Rs> resultType) {
        return execute(request(GET, apiUrl + endpoint).build(), null, resultType);
    }

    /**
     * Executing request and construction response in {@link RetryExecutor}
     */
    protected <Rs> CompletionStage<Rs> execute(Request rq, String idempotenceKey, Class<Rs> resultType) {
        return executor.getFutureWithRetry((ctx) ->
                httpClient.executeRequest(rq).toCompletableFuture()
                        .thenApply(res -> readResponse(res, idempotenceKey, resultType))
                        .exceptionally(e -> mapIOExceptions(e, idempotenceKey)));
    }

    /**
     * Matching response code and returning object of given result type in case of success
     * or throwing {@link YandexCheckoutException} inheritor
     */
    protected <Rs> Rs readResponse(Response response, String idempotenceKey, Class<Rs> resultType) {
        switch (response.getStatusCode()) {
            case 200:
                return mapper.read(response.getResponseBody(), resultType);
            case 401:
                throw new YandexCheckoutAuthorizeException(idempotenceKey);
            case 403:
                throw new YandexCheckoutForbiddenException(idempotenceKey);
            case 404:
                throw new YandexCheckoutNotFoundException(idempotenceKey);
            case 429:
                throw new YandexCheckoutTooManyRequestsException(idempotenceKey);
            default:
                //trying to parse error details from response
                ErrorDetails errorDetails = Optional.ofNullable(response.getContentType())
                        .filter(ct -> ct.equalsIgnoreCase("application/json"))
                        .map(__ -> response.getResponseBody())
                        .map(body -> mapper.read(body, ErrorDetails.class))
                        .orElse(null);

                if (response.getStatusCode() == 400) {
                    throw new YandexCheckoutBadRequestException(idempotenceKey, errorDetails);
                } else if (response.getStatusCode() >= 500) {
                    throw new YandexCheckoutServerException(idempotenceKey, response.getStatusCode(), errorDetails);
                } else {
                    throw new YandexCheckoutUnknownException(idempotenceKey, response.getStatusCode(), errorDetails);
                }
        }
    }

    /**
     * Unwrap {@link CompletionException} and wrap inner {@link IOException} and {@link UncheckedIOException} into
     * {@link YandexCheckoutIOException} with idempotence key (may be null, GET requests doesn't have idempotence key)
     */
    protected <Rs> Rs mapIOExceptions(Throwable e, String idempotenceKey) {
        if (e instanceof CompletionException) {
            Throwable cause = e.getCause();

            if (cause instanceof IOException) {
                throw new YandexCheckoutIOException(idempotenceKey, (IOException) cause);
            } else if (cause instanceof UncheckedIOException) {
                throw new YandexCheckoutIOException(idempotenceKey, ((UncheckedIOException) cause).getCause());
            }
        }

        if (e instanceof RuntimeException) {
            throw (RuntimeException) e;
        }

        throw new RuntimeException(e);
    }


    /**
     * Yandex.Checkout API client builder
     */
    public static class Builder {
        private final Realm realm;

        private AsyncHttpClientConfig clientConfig;
        private RetryExecutor retryExecutor;
        private JsonMapper jsonMapper;
        private String apiUrl;

        public Builder(String storeId, String secretKey) {
            this.realm = new Realm.Builder(storeId, secretKey)
                    .setScheme(Realm.AuthScheme.BASIC)
                    .build();
        }

        public Builder withHttpClientConfig(AsyncHttpClientConfig clientConfig) {
            this.clientConfig = clientConfig;
            return this;
        }

        public Builder withRetryExecutor(RetryExecutor executor) {
            this.retryExecutor = executor;
            return this;
        }

        public Builder withRetryScheduledExecutor(ScheduledExecutorService executor) {
            this.retryExecutor = getDefaultRetryExecutor(executor);
            return this;
        }

        public Builder withJsonMapper(JsonMapper mapper) {
            this.jsonMapper = mapper;
            return this;
        }

        public Builder withTestApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
            return this;
        }

        public YandexCheckoutClient build() {
            //create AsyncHttpClient instance using custom or default configuration
            AsyncHttpClientConfig asyncHttpClientConfig = Optional.ofNullable(this.clientConfig)
                    .map(DefaultAsyncHttpClientConfig.Builder::new)
                    .orElseGet(this::getDefaultClientConfigBuilder)
                    .setRealm(realm)
                    .build();
            AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient(asyncHttpClientConfig);

            //get or create default RetryExecutor instance
            RetryExecutor retryExecutor = Optional.ofNullable(this.retryExecutor)
                    .orElseGet(this::getDefaultRetryExecutor);

            //get or create default JsonMapper instance
            JsonMapper jsonMapper = Optional.ofNullable(this.jsonMapper)
                    .orElseGet(this::getDefaultJsonMapper);

            //get or use default API url
            String apiUrl = Optional.ofNullable(this.apiUrl).orElse(API_URL);

            return new YandexCheckoutClient(asyncHttpClient, retryExecutor, jsonMapper, apiUrl);
        }

        public DefaultAsyncHttpClientConfig.Builder getDefaultClientConfigBuilder() {
            return config();
        }

        public RetryExecutor getDefaultRetryExecutor() {
            return getDefaultRetryExecutor(Executors.newScheduledThreadPool(ForkJoinPool.getCommonPoolParallelism()));
        }

        public RetryExecutor getDefaultRetryExecutor(ScheduledExecutorService executorService) {
            return new AsyncRetryExecutor(executorService)
                    .retryIf(e -> (e.getCause() != null &&
                            (e.getCause() instanceof YandexCheckoutIOException ||
                             e.getCause() instanceof YandexCheckoutServerException ||
                             e.getCause() instanceof YandexCheckoutTooManyRequestsException)))
                    .withExponentialBackoff(RETRIES_EXP_DELAY_INIT, RETRIES_EXP_DELAY_MULTIPLIER)
                    .withMaxDelay(RETRIES_DELAY_MAX)
                    .withMaxRetries(RETRIES_COUNT_MAX)
                    .withUniformJitter();
        }

        public JsonMapper getDefaultJsonMapper() {
            return new DefaultJsonMapperImpl();
        }
    }
}
