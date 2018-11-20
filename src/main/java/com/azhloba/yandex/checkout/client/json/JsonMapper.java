package com.azhloba.yandex.checkout.client.json;

import com.azhloba.yandex.checkout.client.request.YandexCheckoutRequest;

/**
 * Interface for JSON serializer/deserializer used by Yandex.Checkout client
 *
 * @author Aleh Zhloba
 */
public interface JsonMapper {

    /**
     * Method to deserialize JSON content from the given string to given model type.
     * Used for "model." package classes only
     */
    <T> T read(String jsonString, Class<T> tClass);

    /**
     * Method to serialize request object as a JSON string
     */
    String write(YandexCheckoutRequest requestModel);

}
