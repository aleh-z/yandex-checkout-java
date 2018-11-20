package com.azhloba.yandex.checkout.client;

/**
 * @author Aleh Zhloba
 */
public final class Constants {

    public final static String API_URL = "https://payment.yandex.net/api/v3/";


    public final static String IDEMPOTENCE_KEY_HEADER = "Idempotence-Key";


    public final static int RETRIES_COUNT_MAX = 4;

    public final static int RETRIES_EXP_DELAY_INIT = 500;

    public final static int RETRIES_EXP_DELAY_MULTIPLIER = 2;

    public final static int RETRIES_DELAY_MAX = 10_000; //10sec


    private Constants() {}
}
