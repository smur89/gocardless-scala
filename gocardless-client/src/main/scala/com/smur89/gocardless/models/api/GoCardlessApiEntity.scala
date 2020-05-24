package com.smur89.gocardless.models.api

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

sealed trait GoCardlessApiEntity extends Product with Serializable

object GoCardlessApiEntity {
  implicit val decoder: Decoder[GoCardlessApiEntity] = deriveDecoder
  implicit val encoder: Encoder[GoCardlessApiEntity] = deriveEncoder
}

final case class Customer(
  addressLine1:          String,
  addressLine2:          String,
  addressLine3:          String,
  city:                  String,
  companyName:           String,
  countryCode:           String,
  createdAt:             String,
  danishIdentityNumber:  String,
  email:                 String,
  familyName:            String,
  givenName:             String,
  id:                    String,
  language:              String,
  metadata:              Map[String, String],
  phoneNumber:           String,
  postalCode:            String,
  region:                String,
  swedishIdentityNumber: String
) extends GoCardlessApiEntity

object Customer {
  implicit val decoder: Decoder[Customer] = deriveDecoder[Customer]
  implicit val encoder: Encoder[Customer] = deriveEncoder[Customer]
}
