package com.azhloba.yandex.checkout.mock

import akka.http.scaladsl.server.Directives._
import com.azhloba.yandex.checkout.client.model.PaymentStatus
import com.azhloba.yandex.checkout.mock.MockData._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Testing payment API methods
  *
  * @author Aleh Zhloba
  */
class PaymentSpec extends FlatSpec with MockServerSpec with Matchers {

  override def route =
    pathPrefix("payments" / testPaymentId) {
      get {
        waitingForCapturePaymentJson.response
      } ~
      post {
        path("capture") {
          succeededPaymentJson.response
        } ~
        path("cancel") {
          canceledPaymentJson.response
        }
      }
    } ~
    path("payments") {
      post {
        pendingPaymentJson.response
      }
    }

  "Yandex Checkout Client" should "get payment" in {
    val res = awaitResult(cli.getPayment(testPaymentId))

    res.getStatus shouldBe PaymentStatus.WAITING_FOR_CAPTURE
    res.getId shouldBe testPaymentId
    res.getExpiresAt.get shouldBe testExpiresAt
  }

  it should "create payment" in {
    awaitResult(cli.createPayment(testAmount)) shouldBe testPendingPayment
  }

  it should "capture payment" in {
    val res = awaitResult(cli.capturePayment(testPaymentId))

    res.getStatus shouldBe PaymentStatus.SUCCEEDED
    res.getRefundedAmount.get shouldBe testRefundedAmount
    res.getPaymentMethod shouldBe testSucceededPaymentMethod
    res.getCapturedAt.get shouldBe testCapturedAt
    res.getPaid shouldBe true
  }

  it should "cancel payment" in {
    val res = awaitResult(cli.cancelPayment(testPaymentId))

    res.getStatus shouldBe PaymentStatus.CANCELED
    res.getPaid shouldBe false
  }

}
