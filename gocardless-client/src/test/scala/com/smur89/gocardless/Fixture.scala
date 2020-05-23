package com.smur89.gocardless

import java.net.http.HttpResponse
import java.util.UUID

import com.smur89.gocardless.client.{AkkaHttp, GoCardlessClient}
import com.smur89.gocardless.models._
import org.mockito.MockitoSugar
import java.time.Instant

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpResponse
import akka.stream.Materializer
import io.circe.syntax._
import org.mockito.MockitoSugar

import cats.data.EitherT

import cats.syntax.option._

import scala.util.Properties
import cats.data.EitherT

import scala.collection.parallel.Task
import scala.util.Properties

trait Fixture extends MockitoSugar {
  type TestContext[A] = EitherT[Task, GoCardlessClientError, A]

  implicit val system:       ActorSystem  = ActorSystem()
  implicit val materializer: Materializer = Materializer(system)

  val config: GoCardlessConfiguration = GoCardlessConfiguration(
    "https://api-sandbox.gocardless.com/",
    Properties.envOrElse("GOCARDLESS_ACCESS_TOKEN", "my_gocardless_access_token")
  )

  val mockHttpSender: AkkaHttp[TestContext] = {
    val realHttpSender = new AkkaHttp[TestContext](config)
    spy(realHttpSender)
  }

  val client = new GoCardlessClient[TestContext, HttpResponse](mockHttpSender)

  val unproccessableEntityError: UnprocessableEntity = UnprocessableEntity(
    "validation_failed",
    422,
    "Validation failed",
    "https://developer.gocardless.com/#validation_failed",
    UUID.fromString("dd50eaaf-8213-48fe-90d6-5466872efbc4"),
    Seq(
      Validation("must be a number", "branch_code"),
      Validation("is the wrong length (should be 8 characters)", "branch_code")
    )
  )

  val conflictError: Conflict = Conflict(
    "validation_failed",
    409,
    "Bank account already exists",
    "https://developer.gocardless.com/#bank_account_exists",
    UUID.fromString("bd271b37-a2f5-47c8-b461-040dfe0e9cb1"),
    Seq(Default("Bank account already exists", "bank_account_exists"))
  )

  val badRequestError: BadRequest = BadRequest(
    "invalid_api_usage",
    400,
    "Invalid document structure",
    "https://developer.gocardless.com/#invalid_document_structure",
    UUID.fromString("bd271b37-a2f5-47c8-b461-040dfe0e9cb1"),
    Seq(Default("Invalid document structure", "invalid_document_structure"))
  )

}
