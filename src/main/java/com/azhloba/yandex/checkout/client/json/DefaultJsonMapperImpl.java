package com.azhloba.yandex.checkout.client.json;

import com.azhloba.yandex.checkout.client.model.Notification;
import com.azhloba.yandex.checkout.client.model.Payment;
import com.azhloba.yandex.checkout.client.model.Refund;
import com.azhloba.yandex.checkout.client.request.YandexCheckoutRequest;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.zalando.jackson.datatype.money.MoneyModule;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * Default JsonMapper implementation using Jackson
 *
 * @author Aleh Zhloba
 */
public class DefaultJsonMapperImpl implements JsonMapper {

    protected final ObjectMapper mapper;

    public DefaultJsonMapperImpl() {
        SimpleModule customModule = new SimpleModule();
        customModule.addDeserializer(Notification.class, new NotificationDeserializer());

        mapper = new ObjectMapper().registerModule(customModule)
                .registerModule(new Jdk8Module().configureAbsentsAsNulls(true))
                .registerModule(new JavaTimeModule())
                .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES))
                .registerModule(new MoneyModule().withAmountFieldName("value").withDefaultFormatting())
                .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Override
    public <T> T read(String jsonString, Class<T> tClass) {
        try {
            return mapper.readValue(jsonString, tClass);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public String write(YandexCheckoutRequest requestModel) {
        try {
            return mapper.writeValueAsString(requestModel);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Custom deserializer for {@link Notification} class.
     * Can holds {@link Payment} or {@link Refund} object
     */
    public class NotificationDeserializer extends StdDeserializer<Notification> {

        public NotificationDeserializer() {
            this(null);
        }

        public NotificationDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Notification deserialize(JsonParser jp, DeserializationContext ctxt)
                throws IOException {
            ObjectCodec codec = jp.getCodec();
            JsonNode node = codec.readTree(jp);

            String type = node.get("type").textValue();
            if (!"notification".equals(type)) {
                throw new IOException("unsupported notification type");
            }

            String event = node.get("event").textValue();
            JsonNode object = node.get("object");

            if (event.startsWith("payment.")) {
                return new Notification(type, event, codec.treeToValue(object, Payment.class));
            } else if (event.startsWith("refund.")) {
                return new Notification(type, event, codec.treeToValue(object, Refund.class));
            }

            throw new IOException("unsupported notification event model");
        }
    }
}
