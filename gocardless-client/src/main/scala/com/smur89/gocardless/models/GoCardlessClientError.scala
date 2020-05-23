package com.smur89.gocardless.models

import java.util.UUID

import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.circe.{Decoder, Encoder}

trait Error                      extends GoCardlessResponse
sealed trait GoCardlessErrorType extends Error { val message: String }
final case class Default(message: String, reason: String) extends GoCardlessErrorType
final case class Validation(message: String, field: String) extends GoCardlessErrorType

sealed trait GoCardlessClientError[A <: GoCardlessErrorType] extends GoCardlessResponse {
  val `type`:           String
  val code:             Int
  val message:          String
  val documentationUrl: String
  val requestId:        UUID
  val errors:           Seq[A]
}

object GoCardlessClientError {
  implicit val encoder: Encoder[GoCardlessClientError[GoCardlessErrorType]] = deriveConfiguredEncoder
  implicit val decoder: Decoder[GoCardlessClientError[GoCardlessErrorType]] = deriveConfiguredDecoder
}

final case class UnprocessableEntity(
  `type`:           String,
  code:             Int,
  message:          String,
  documentationUrl: String,
  requestId:        UUID,
  errors:           Seq[Validation]
) extends GoCardlessClientError[Validation]

object UnprocessableEntity {
  implicit val encoder: Encoder[UnprocessableEntity] = deriveConfiguredEncoder
  implicit val decoder: Decoder[UnprocessableEntity] = deriveConfiguredDecoder
}

case class Conflict(
  `type`:           String,
  code:             Int,
  message:          String,
  documentationUrl: String,
  requestId:        UUID,
  errors:           Seq[Default]
) extends GoCardlessClientError[Default]

object Conflict {
  implicit val encoder: Encoder[Conflict] = deriveConfiguredEncoder
  implicit val decoder: Decoder[Conflict] = deriveConfiguredDecoder
}

case class BadRequest(
  `type`:           String,
  code:             Int,
  message:          String,
  documentationUrl: String,
  requestId:        UUID,
  errors:           Seq[Default]
) extends GoCardlessClientError[Default]

object BadRequest {
  implicit val encoder: Encoder[BadRequest] = deriveConfiguredEncoder
  implicit val decoder: Decoder[BadRequest] = deriveConfiguredDecoder
}

final case class ServerError(message: String) extends Error
final case class ServiceUnavailable(message: String) extends Error

object ErrorMessages {
  val BadGateway =
    "Amplitude received an invalid response from the upstream server it accessed in attempting to fulfill the request"
  val InternalServerError =
    "Amplitude encountered an unexpected condition which prevented it from fulfilling the request"
  val GatewayTimeout = "Amplitude did not receive a timely response from the upstream server"

  val ServiceUnavailable =
    "Amplitude is currently unable to handle the request due to a temporary overloading or maintenance of the server."

  val Unexpected =
    "Amplitude returned an unexpected response"
}
