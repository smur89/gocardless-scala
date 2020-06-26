package com.smur89.gocardless.models.api

import java.util.UUID

import io.circe.generic.extras.semiauto._
import io.circe.{Decoder, Encoder}

final case class GoCardlessApiError(error: GoCardlessError)
object GoCardlessApiError {
  implicit lazy val encoder: Encoder[GoCardlessApiError] = deriveConfiguredEncoder
  implicit lazy val decoder: Decoder[GoCardlessApiError] = deriveConfiguredDecoder
}

trait GcError

final case class GenericError(msg: String) extends GcError

final case class GoCardlessError(
  `type`:           String,
  code:             Int,
  message:          String,
  documentationUrl: String,
  requestId:        UUID,
  errors:           Seq[GoCardlessErrorType]
) extends GoCardlessResponse

object GoCardlessError {
  implicit def encoder: Encoder[GoCardlessError] = deriveConfiguredEncoder
  implicit def decoder: Decoder[GoCardlessError] = deriveConfiguredDecoder
}

final case class ServerError(message: String) extends GcError
final case class ServiceUnavailable(message: String) extends GcError
