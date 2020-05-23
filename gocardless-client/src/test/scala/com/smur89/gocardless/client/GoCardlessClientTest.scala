package com.smur89.gocardless.client

import com.smur89.gocardless.models.GoCardlessClientError
import com.smur89.gocardless.{BaseSpec, Fixture}
import org.scalatest.concurrent.ScalaFutures

import cats.data.EitherT

import cats.syntax.either._

import scala.io.Source

class GoCardlessClientTest extends BaseSpec with ScalaFutures {

  "Amplitude Client" when {
    "Successful request" should {
      "return SuccessSummary" in new Fixture {
        val response: TestContext[HttpResponse] = EitherT.fromEither {
          val requestJson = Source.fromResource("success_summary.json").getLines.mkString
          HttpResponse(status = StatusCodes.OK, entity = HttpEntity(ContentTypes.`application/json`, requestJson))
            .asRight[GoCardlessClientError]
        }
        when(mockHttpSender.makeRequest).thenReturn(_ => response)

        client
          .send(batchPost)
          .value
          .runSyncUnsafe()
          .map {
            case SuccessSummary(code, _, _, _) => code shouldBe StatusCodes.OK.intValue
            case _                             => fail()
          }
          .right
          .value
      }
    }

    "Error request" should {
      "return InvalidRequestError for BadRequest" in new Fixture {
        val response: TestContext[HttpResponse] = EitherT.fromEither {
          val requestJson = Source.fromResource("conflict.json").getLines.mkString
          HttpResponse(
            status = StatusCodes.BadRequest,
            entity = HttpEntity(ContentTypes.`application/json`, requestJson)
          ).asRight[GoCardlessClientError]
        }
        when(mockHttpSender.makeRequest).thenReturn(_ => response)

        client
          .send(batchPost)
          .value
          .runSyncUnsafe()
          .leftMap {
            case InvalidRequestError(code, _, _, _, _) => code shouldBe StatusCodes.BadRequest.intValue
            case _                                     => fail()
          }
          .left
          .value
      }

      "return PayloadTooLargeError for PayloadTooLarge" in new Fixture {
        val response: TestContext[HttpResponse] = EitherT.fromEither {
          val requestJson = Source.fromResource("payload_too_large_error.json").getLines.mkString
          HttpResponse(
            status = StatusCodes.PayloadTooLarge,
            entity = HttpEntity(ContentTypes.`application/json`, requestJson)
          ).asRight[GoCardlessClientError]
        }
        when(mockHttpSender.makeRequest).thenReturn(_ => response)

        client
          .send(batchPost)
          .value
          .runSyncUnsafe()
          .leftMap {
            case PayloadTooLargeError(code, _) => code shouldBe StatusCodes.PayloadTooLarge.intValue
            case _                             => fail()
          }
          .left
          .value
      }

      "return TooManyRequestsForDeviceError for TooManyRequests" in new Fixture {
        val response: TestContext[HttpResponse] = EitherT.fromEither {
          val requestJson = Source.fromResource("bad_request.json").getLines.mkString
          HttpResponse(
            status = StatusCodes.TooManyRequests,
            entity = HttpEntity(ContentTypes.`application/json`, requestJson)
          ).asRight[GoCardlessClientError]
        }
        when(mockHttpSender.makeRequest).thenReturn(_ => response)

        client
          .send(batchPost)
          .value
          .runSyncUnsafe()
          .leftMap {
            case TooManyRequestsForDeviceError(code, _, _, _, _, _) =>
              code shouldBe StatusCodes.TooManyRequests.intValue
            case _ => fail()
          }
          .left
          .value
      }

      "return ServerError for InternalServerError" in new Fixture {
        val response: TestContext[HttpResponse] = EitherT.fromEither {
          HttpResponse(
            status = StatusCodes.InternalServerError,
            entity = HttpEntity.empty(ContentTypes.`application/json`)
          ).asRight[GoCardlessClientError]
        }
        when(mockHttpSender.makeRequest).thenReturn(_ => response)

        client
          .send(batchPost)
          .value
          .runSyncUnsafe()
          .leftMap {
            case ServerError(msg) => msg shouldBe ErrorMessages.InternalServerError
            case _                => fail()
          }
          .left
          .value
      }

      "return ServerError for BadGateway" in new Fixture {
        val response: TestContext[HttpResponse] = EitherT.fromEither {
          HttpResponse(status = StatusCodes.BadGateway, entity = HttpEntity.empty(ContentTypes.`application/json`))
            .asRight[GoCardlessClientError]
        }
        when(mockHttpSender.makeRequest).thenReturn(_ => response)

        client
          .send(batchPost)
          .value
          .runSyncUnsafe()
          .leftMap {
            case ServerError(msg) => msg shouldBe ErrorMessages.BadGateway
            case _                => fail()
          }
          .left
          .value
      }

      "return ServerError for GatewayTimeout" in new Fixture {
        val response: TestContext[HttpResponse] = EitherT.fromEither {
          HttpResponse(status = StatusCodes.GatewayTimeout, entity = HttpEntity.empty(ContentTypes.`application/json`))
            .asRight[GoCardlessClientError]
        }
        when(mockHttpSender.makeRequest).thenReturn(_ => response)

        client
          .send(batchPost)
          .value
          .runSyncUnsafe()
          .leftMap {
            case ServerError(msg) => msg shouldBe ErrorMessages.GatewayTimeout
            case _                => fail()
          }
          .left
          .value
      }

      "return ServiceUnavailable for ServiceUnavailable" in new Fixture {
        val response: TestContext[HttpResponse] = EitherT.fromEither {
          HttpResponse(
            status = StatusCodes.ServiceUnavailable,
            entity = HttpEntity.empty(ContentTypes.`application/json`)
          ).asRight[GoCardlessClientError]
        }
        when(mockHttpSender.makeRequest).thenReturn(_ => response)

        client
          .send(batchPost)
          .value
          .runSyncUnsafe()
          .leftMap {
            case ServiceUnavailable(msg) => msg shouldBe ErrorMessages.ServiceUnavailable
            case _                       => fail()
          }
          .left
          .value
      }

    }
  }
}
