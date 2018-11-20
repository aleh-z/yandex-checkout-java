package com.azhloba.yandex.checkout.mock

import java.util.concurrent.{CompletionStage, TimeUnit}

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives.{complete, _}
import akka.http.scaladsl.server.directives.Credentials
import akka.http.scaladsl.server.{Route, StandardRoute}
import akka.stream.ActorMaterializer
import com.azhloba.yandex.checkout.client.YandexCheckoutClient
import org.scalatest.{BeforeAndAfter, FlatSpec}

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContextExecutor, Future}
import scala.util.Try

/**
  * Trait for mocking http server
  *
  * @author Aleh Zhloba
  */
trait MockServerSpec extends FlatSpec with BeforeAndAfter {

  val storeId = "storeId"
  val secretKey = "secretKey"

  val location = "localhost"
  val port = 9009

  val cli = YandexCheckoutClient.builder(storeId, secretKey)
    .withTestApiUrl(s"http://$location:$port/")
    .build()

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  var binding: Future[Http.ServerBinding] = _

  def authenticator(credentials: Credentials): Option[Boolean] =
    credentials match {
      case c @ Credentials.Provided(_) if c.verify(secretKey) => Some(true)
      case _ => None
    }

  //set up new http server
  before {
    val securedRoute: Route = authenticateBasic("secure site", authenticator) { _ =>
      route
    }

    binding = Http().bindAndHandle(securedRoute, location, port)
  }

  //stop http server
  after {
    Await.result(binding.flatMap(b => b.terminate(5 second)), 10 second)
  }

  //routes to implement in tests
  def route: Route

  implicit class String2JsonOps(val jsonModel: String) {
    val response: StandardRoute = response(StatusCodes.OK)

    def response(statusCode: StatusCode): StandardRoute = {
      complete(HttpResponse(status = statusCode, entity = HttpEntity(ContentTypes.`application/json`, jsonModel)))
    }
  }

  def awaitResult[T](future: CompletionStage[T]): T = {
    future.toCompletableFuture.get(10, TimeUnit.SECONDS)
  }

  def awaitException(future: CompletionStage[_]) = {
   throw Try(future.toCompletableFuture.get()).failed.get.getCause
  }

}
