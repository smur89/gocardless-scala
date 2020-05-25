package com.smur89.gocardless.models.api

import io.circe.generic.extras.auto._
import io.circe.syntax._
import io.circe.{Decoder, Encoder, Json}

import cats.syntax.functor._

sealed trait GoCardlessErrorType { val message: String; val links: Option[Map[String, Json]] }

object GoCardlessErrorType {
  implicit val encodeEvent: Encoder[GoCardlessErrorType] = Encoder.instance {
    case validation: Validation => validation.asJson
    case default:    Default    => default.asJson
  }

  implicit val decodeEvent: Decoder[GoCardlessErrorType] =
    List[Decoder[GoCardlessErrorType]](
      Decoder[Default].widen,
      Decoder[Validation].widen
    ).reduceLeft(_ or _)
}

final case class Default(message: String, reason: String, links: Option[Map[String, Json]] = None)
  extends GoCardlessErrorType

final case class Validation(
  message:        String,
  field:          String,
  requestPointer: Option[String] = None,
  links:          Option[Map[String, Json]] = None
) extends GoCardlessErrorType
