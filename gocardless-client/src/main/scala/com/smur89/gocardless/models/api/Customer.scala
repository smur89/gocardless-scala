package com.smur89.gocardless.models.api

import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.circe.{Decoder, Encoder}

case class Customer(
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
  implicit val encoder: Encoder[Customer] = deriveConfiguredEncoder
  implicit val decoder: Decoder[Customer] = deriveConfiguredDecoder
}
