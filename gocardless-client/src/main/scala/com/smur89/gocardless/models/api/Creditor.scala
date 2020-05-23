package com.smur89.gocardless.models.api

import io.circe.generic.extras.semiauto.{deriveConfiguredDecoder, deriveConfiguredEncoder}
import io.circe.{Decoder, Encoder}

case class Creditor(
  addressLine1:                        String,
  addressLine2:                        String,
  addressLine3:                        String,
  canCreateRefunds:                    Boolean,
  city:                                String,
  countryCode:                         String,
  createdAt:                           String,
  customPaymentPagesEnabled:           Boolean,
  fxPayoutCurrency:                    Nothing,
  id:                                  String,
  links:                               AnyRef,
  logoUrl:                             String,
  mandateImportsEnabled:               Boolean,
  merchantResponsibleForNotifications: Boolean,
  name:                                String,
  postalCode:                          String,
  region:                              String,
  schemeIdentifiers:                   Nothing,
  verificationStatus:                  Nothing
) extends GoCardlessApiEntity

object Creditor {
  implicit val encoder: Encoder[Creditor] = deriveConfiguredEncoder
  implicit val decoder: Decoder[Creditor] = deriveConfiguredDecoder
}
