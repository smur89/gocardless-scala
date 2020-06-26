package com.smur89.gocardless.models.api

import com.smur89.gocardless.models.api.Ids.CustomerId
import io.circe.generic.extras.semiauto._
import io.circe.{Codec, Decoder, Encoder}
import io.estatico.newtype.macros.newtype

object Ids {
  implicit val stringCodec: Codec[String] = Codec.from[String](Decoder.decodeString, Encoder.encodeString)

  @newtype case class CustomerId(coerce: String)
  object CustomerId {
    implicit val encoder: Codec[CustomerId] = deriving
  }
}

sealed trait GoCardlessApiEntity extends Product with Serializable { val id: CustomerId }

object GoCardlessApiEntity {
  implicit val codec: Codec[GoCardlessApiEntity] = deriveConfiguredCodec
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
  id:                    CustomerId,
  language:              String,
  metadata:              Map[String, String],
  phoneNumber:           String,
  postalCode:            String,
  region:                String,
  swedishIdentityNumber: String
) extends GoCardlessApiEntity

object Customer {
  implicit val encoder: Codec[Customer] = deriveConfiguredCodec
}
