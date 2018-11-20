package com.azhloba.yandex.checkout.mock

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives.{get, path, pathPrefix, post, _}
import com.azhloba.yandex.checkout.client.YandexCheckoutClient
import com.azhloba.yandex.checkout.client.exception._
import com.azhloba.yandex.checkout.mock.MockData._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Testing handling errors
  *
  * @author Aleh Zhloba
  */
class ErrorSpec extends FlatSpec with MockServerSpec with Matchers {

  var retryUuid: Option[String] = None

  override def route = {
    pathPrefix("payments" / testPaymentId) {
      get {
        waitingForCapturePaymentJson.response
      } ~
      post {
        path("capture") {
          "forbidden".response(Forbidden)
        } ~
        path("cancel") {
          "too_many_requests".response(TooManyRequests)
        }
      }
    } ~
    path("payments") {
      post {
        badRequestJson.response(BadRequest)
      }
    } ~
    path("refunds" / testRefundId) {
      get {
        "{}".response(ServiceUnavailable)
        }
      }
    } ~
    path("refunds") {
      post {
        headerValueByName("Idempotence-Key") { key =>
          retryUuid match {
            case Some(uuid) if uuid.equals(key) => refundJson.response
            case _ =>
              retryUuid = Some(key)
              "too_many_requests".response(TooManyRequests)
        }
      }
    }
  }

  val wrongCredsCli = YandexCheckoutClient.builder(storeId, "wrongkey")
    .withTestApiUrl(s"http://$location:$port/")
    .build()

  val wrongUrlCli = YandexCheckoutClient.builder(storeId, secretKey)
    .withTestApiUrl("https://google.com")
    .build()

  "Yandex Checkout Client" should "throw AuthorizeException with wrong creds" in {
    intercept[YandexCheckoutAuthorizeException] {
      awaitException(wrongCredsCli.getPayment(testPaymentId))
    }
  }

  it should "throw IOException with wrong api url" in {
    intercept[YandexCheckoutIOException] {
      awaitException(wrongUrlCli.getPayment(testPaymentId))
    }
  }

  it should "throw BadRequestException on 400 response" in {
    val e = intercept[YandexCheckoutBadRequestException] {
      awaitException(cli.createPayment(testAmount, testUuid))
    }

    e.getErrorDetails shouldBe testErrorDetails
    e.getIdempotenceKey.get shouldBe testUuid
  }

  it should "throw ForbiddenException on 403 response" in {
    intercept[YandexCheckoutForbiddenException] {
      awaitException(cli.capturePayment(testPaymentId))
    }
  }

  it should "throw NotFoundException on 404 response" in {
    intercept[YandexCheckoutNotFoundException] {
      awaitException(cli.getPayment("wrongId"))
    }
  }

  it should "throw TooManyRequestsException on 429 response (after configured number of tries)" in {
    intercept[YandexCheckoutTooManyRequestsException] {
      awaitException(cli.cancelPayment(testPaymentId))
    }
  }

  it should "throw ServerException on 5xx response (after configured number of tries)" in {
    intercept[YandexCheckoutServerException] {
      awaitException(cli.getRefund(testRefundId))
    }.getStatusCode shouldBe 503
  }

  it should "retry request in case of TooManyRequestsException" in {
    awaitResult(cli.createRefund(testPaymentId)).getId shouldBe testRefundId
  }

}
