# yandex-checkout-java

[![](https://jitpack.io/v/aleh-z/yandex-checkout-java.svg)](https://jitpack.io/#aleh-z/yandex-checkout-java) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

An asynchronous Java client for Yandex.Checkout API / Асинхронный Java клиент Яндекс.Касса API

Implemented using [Async Http Client](https://github.com/AsyncHttpClient/async-http-client), [async-retry](https://github.com/nurkiewicz/async-retry), [Jackson](https://github.com/FasterXML/jackson) and [Moneta](http://javamoney.github.io/ri.html).

## Prerequisites

 * JDK 8+

## Installation

Maven:

```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```
```xml
<dependency>
    <groupId>com.github.aleh-z</groupId>
    <artifactId>yandex-checkout-java</artifactId>
    <version>0.9.0</version>
</dependency>
```

SBT:

```scala
resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies += "com.github.aleh-z" % "yandex-checkout-java" % "0.9.0"	
```

## Usage

Simply build client using store’s Yandex.Checkout ID and secret key

```java
YandexCheckoutClient.builder("store-id", "secret-key").build();
```
or provide an additional configuration

```java
var client = YandexCheckoutClient.builder("store-id", "secret-key")
  //custom AsyncHttpClient config
  .withHttpClientConfig(config) 
  
  //manually configured retry executor
  .withRetryExecutor(retryExecutor) 
  //or just scheduled executor to use in default retry executor
  .withRetryScheduledExecutor(scheduledExecutor) 
  
  //custom implementation of JsonMapper
  .withJsonMapper(jsonMapper)
  .build();
```
Requests example:

```java
CompletionStage<Payment> getPaymentFuture = client.getPayment(paymentId);
CompletionStage<Payment> createPaymentFuture = client.createPayment(amount);
CompletionStage<Payment> createAnotherPaymentFuture = client.createPaymentBuilder(amount)
  .withDescription("test_payment");
  .execute();
```

To parse notifications use this method:

```java
Notification notification = client.readNotification(jsonRequestBodyString);
```

Futures can compleats with some type of `YandexCheckoutException` which holds idempotence key (if exists) and possible additional information. Please see `com.azhloba.yandex.checkout.client.exception` package for details.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
