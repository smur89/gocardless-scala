package com.smur89.gocardless.models

import java.util.UUID

import io.circe.generic.extras.semiauto._
import io.circe.{Decoder, Encoder}
import com.smur89.gocardless.models.models.codecConfiguration

sealed trait Error               extends GoCardlessResponse
sealed trait GoCardlessErrorType extends Error { val message: String }
final case class Default(message: String, reason: String) extends GoCardlessErrorType
object Default {
  implicit val encoder: Encoder[Default] = deriveConfiguredEncoder
  implicit val decoder: Decoder[Default] = deriveConfiguredDecoder
}
final case class Validation(message: String, field: String) extends GoCardlessErrorType
object Validation {
  implicit val encoder: Encoder[Validation] = deriveConfiguredEncoder
  implicit val decoder: Decoder[Validation] = deriveConfiguredDecoder
}
sealed trait GoCardlessClientError[A <: GoCardlessErrorType] extends GoCardlessResponse {
  val `type`:           String
  val code:             Int
  val message:          String
  val documentationUrl: String
  val requestId:        UUID
  val errors:           Seq[A]
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
