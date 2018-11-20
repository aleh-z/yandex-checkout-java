package com.azhloba.yandex.checkout.mock

import java.time.Instant
import java.util.UUID

import com.azhloba.yandex.checkout.client.model._
import javax.money.MonetaryAmount
import org.javamoney.moneta.Money

/**
  * Mock data contains JSON response examples from official documentation
  * https://checkout.yandex.com/docs/checkout-api/#using-the-api
  *
  * @author Aleh Zhloba
  */
object MockData {

  val testPaymentId = "22e12f66-000f-5000-8000-18db351245c7"
  val testAmount: MonetaryAmount = Money.of(2, "RUB")
  val testConfirmation = new Confirmation("redirect", null, "https://www.merchant-website.com/return_url",
    "https://money.yandex.ru/payments/external/confirmation?orderId=22e12f66-000f-5000-8000-18db351245c7")
  val testPendingPaymentMethod = new PaymentMethod("bank_card", "22e12f66-000f-5000-8000-18db351245c7", false,
    null, null, null, null, null, null, null, null)
  val testPendingPayment = new Payment(testPaymentId, PaymentStatus.PENDING, testAmount, "Order No. 72",
    null, testPendingPaymentMethod, null, Instant.parse("2018-07-18T10:51:18.139Z"),
    null, testConfirmation, false, null, false, null,
    java.util.Map.of("test_meta", "test_value"), null, null)
  val pendingPaymentJson =
    """
      |{
      |  "id": "22e12f66-000f-5000-8000-18db351245c7",
      |  "status": "pending",
      |  "paid": false,
      |  "amount": {
      |    "value": "2.00",
      |    "currency": "RUB"
      |  },
      |  "confirmation": {
      |    "type": "redirect",
      |    "return_url": "https://www.merchant-website.com/return_url",
      |    "confirmation_url": "https://money.yandex.ru/payments/external/confirmation?orderId=22e12f66-000f-5000-8000-18db351245c7"
      |  },
      |  "created_at": "2018-07-18T10:51:18.139Z",
      |  "description": "Order No. 72",
      |  "metadata": {"test_meta": "test_value"},
      |  "payment_method": {
      |    "type": "bank_card",
      |    "id": "22e12f66-000f-5000-8000-18db351245c7",
      |    "saved": false
      |  },
      |  "test": false
      |}""".stripMargin


  val testExpiresAt = Instant.parse("2018-07-25T10:52:00.233Z")
  val waitingForCapturePaymentJson =
    """
      |{
      |  "id": "22e12f66-000f-5000-8000-18db351245c7",
      |  "status": "waiting_for_capture",
      |  "paid": true,
      |  "amount": {
      |    "value": "2.00",
      |    "currency": "RUB"
      |  },
      |  "created_at": "2018-07-18T10:51:18.139Z",
      |  "description": "Order No. 72",
      |  "expires_at": "2018-07-25T10:52:00.233Z",
      |  "metadata": {},
      |  "payment_method": {
      |    "type": "bank_card",
      |    "id": "22e12f66-000f-5000-8000-18db351245c7",
      |    "saved": false,
      |    "card": {
      |      "first6": "555555",
      |      "last4": "4444",
      |      "expiry_month": "07",
      |      "expiry_year": "2022",
      |      "card_type": "MasterCard"
      |    },
      |    "title": "Bank card *4444"
      |  },
      |  "test": false
      |}
    """.stripMargin

  val testRefundedAmount = Money.of(0, "RUB")
  val testCapturedAt = Instant.parse("2018-07-18T11:17:33.483Z")
  val testSucceededPaymentMethod = new PaymentMethod("bank_card", "22e12f66-000f-5000-8000-18db351245c7",
    false, "Bank card *4444", null, null,
    new Card("555555", "4444", "2022", "07", "MasterCard"),
    null, null, null, null)
  val succeededPaymentJson =
    """
      |{
      |  "id": "22e12f66-000f-5000-8000-18db351245c7",
      |  "status": "succeeded",
      |  "paid": true,
      |  "amount": {
      |    "value": "2.00",
      |    "currency": "RUB"
      |  },
      |  "captured_at": "2018-07-18T11:17:33.483Z",
      |  "created_at": "2018-07-18T10:51:18.139Z",
      |  "description": "Order No. 72",
      |  "metadata": {},
      |  "payment_method": {
      |    "type": "bank_card",
      |    "id": "22e12f66-000f-5000-8000-18db351245c7",
      |    "saved": false,
      |    "card": {
      |      "first6": "555555",
      |      "last4": "4444",
      |      "expiry_month": "07",
      |      "expiry_year": "2022",
      |      "card_type": "MasterCard"
      |    },
      |    "title": "Bank card *4444"
      |  },
      |  "refunded_amount": {
      |    "value": "0.00",
      |    "currency": "RUB"
      |  },
      |  "test": false
      |}""".stripMargin

  val canceledPaymentJson =
    """
      |{
      |  "id": "22e12f66-000f-5000-8000-18db351245c7",
      |  "status": "canceled",
      |  "paid": false,
      |  "amount": {
      |    "value": "2.00",
      |    "currency": "RUB"
      |  },
      |  "created_at": "2018-07-18T10:51:18.139Z",
      |  "description": "Order No. 72",
      |  "metadata": {},
      |  "payment_method": {
      |    "type": "bank_card",
      |    "id": "22e12f66-000f-5000-8000-18db351245c7",
      |    "saved": false,
      |    "card": {
      |      "first6": "555555",
      |      "last4": "4444",
      |      "expiry_month": "07",
      |      "expiry_year": "2022",
      |      "card_type": "MasterCard"
      |    },
      |    "title": "Bank card *4444"
      |  },
      |  "test": false
      |}""".stripMargin

  val testRefundId = "216749f7-0016-50be-b000-078d43a63ae4"
  val testRefundPaymentId = "216749da-000f-50be-b000-096747fad91e"
  val testRefund = new Refund(testRefundId, testRefundPaymentId, RefundStatus.SUCCEEDED, Instant.parse("2017-10-04T19:27:51.407Z"),
    Money.of(1, "RUB"), null, null)

  val refundJson =
    """
      |{
      |  "id": "216749f7-0016-50be-b000-078d43a63ae4",
      |  "status": "succeeded",
      |  "amount": {
      |    "value": "1",
      |    "currency": "RUB"
      |  },
      |  "created_at": "2017-10-04T19:27:51.407Z",
      |  "payment_id": "216749da-000f-50be-b000-096747fad91e"
      |}""".stripMargin


  val testErrorDetails = new ErrorDetails("error", "ab5a11cd-13cc-4e33-af8b-75a74e18dd09", "invalid_request",
    "Idempotence key duplicated", null, "Idempotence-Key")
  val testUuid = UUID.randomUUID().toString
  val badRequestJson =
    """{
      |    "type": "error",
      |    "id": "ab5a11cd-13cc-4e33-af8b-75a74e18dd09",
      |    "code": "invalid_request",
      |    "description": "Idempotence key duplicated",
      |    "parameter": "Idempotence-Key"
      |  }""".stripMargin



  val notificationJson =
    """{
      |  "type": "notification",
      |  "event": "payment.waiting_for_capture",
      |  "object": {
      |    "id": "22d6d597-000f-5000-9000-145f6df21d6f",
      |    "status": "waiting_for_capture",
      |    "paid": true,
      |    "amount": {
      |      "value": "2.00",
      |      "currency": "RUB"
      |    },
      |    "authorization_details": {
      |      "rrn": "10000000000",
      |      "auth_code": "000000"
      |    },
      |    "created_at": "2018-07-10T14:27:54.691Z",
      |    "description": "Заказ №72",
      |    "expires_at": "2018-07-17T14:28:32.484Z",
      |    "metadata": {},
      |    "payment_method": {
      |      "type": "bank_card",
      |      "id": "22d6d597-000f-5000-9000-145f6df21d6f",
      |      "saved": false,
      |      "card": {
      |        "first6": "555555",
      |        "last4": "4444",
      |        "expiry_month": "07",
      |        "expiry_year": "2021",
      |        "card_type": "MasterCard"
      |      },
      |      "title": "Bank card *4444"
      |    },
      |    "test": false
      |  }
      |}""".stripMargin
}
