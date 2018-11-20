package com.azhloba.yandex.checkout.mock

import com.azhloba.yandex.checkout.client.json.DefaultJsonMapperImpl
import com.azhloba.yandex.checkout.client.model.{Notification, Payment}
import com.azhloba.yandex.checkout.mock.MockData._
import org.scalatest.{FlatSpec, Matchers}

/**
  * Testing Notification object unmarshalling
  *
  * @author Aleh Zhloba
  */
class NotificationSpec extends FlatSpec with Matchers {

  val jsonMapper = new DefaultJsonMapperImpl()

  "Json mapper" should "construct notification model from json string" in {
    val res = jsonMapper.read(notificationJson, classOf[Notification])

    res.getType shouldBe "notification"
    res.getEvent shouldBe "payment.waiting_for_capture"
    res.getObject.asInstanceOf[Payment].getAuthorizationDetails.get.getRrn.get shouldBe "10000000000"
  }

}
