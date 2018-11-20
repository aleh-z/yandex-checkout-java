package com.azhloba.yandex.checkout.mock

import akka.http.scaladsl.server.Directives._
import com.azhloba.yandex.checkout.mock.MockData._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Testing refund API methods
  *
  * @author Aleh Zhloba
  */
class RefundSpec extends FlatSpec with MockServerSpec with Matchers {

  override def route =
    path("refunds" / testRefundId) {
      get {
        refundJson.response
      }
    } ~
    path("refunds") {
      post {
        refundJson.response
      }
    }

  "Yandex Checkout Client" should "get refund" in {
    awaitResult(cli.getRefund(testRefundId)) shouldBe testRefund
  }

  it should "create refund" in {
    awaitResult(cli.createRefund(testPaymentId)).getId shouldBe testRefundId
  }

}
