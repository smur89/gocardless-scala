package com.smur89.gocardless.models.api

import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.circe.{Decoder, Encoder}

case class CreditorBankAccount(
  accountHolderName:   String,
  accountNumberEnding: String,
  accountType:         Nothing,
  bankName:            String,
  countryCode:         String,
  createdAt:           String,
  currency:            String,
  enabled:             Boolean,
  id:                  String,
  links:               AnyRef,
  metadata:            Nothing
) extends GoCardlessApiEntity

object CreditorBankAccount {
  implicit val encoder: Encoder[CreditorBankAccount] = deriveConfiguredEncoder
  implicit val decoder: Decoder[CreditorBankAccount] = deriveConfiguredDecoder
}
