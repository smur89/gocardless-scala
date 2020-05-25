package com.smur89.gocardless

import java.util.UUID

import com.smur89.gocardless.models._
import com.smur89.gocardless.models.api.{Default, GoCardlessApiError, GoCardlessError, Validation}
import io.circe.Printer
import io.circe.syntax._
import org.mockito.MockitoSugar

import cats.syntax.option._

import scala.util.Properties

trait Fixture extends MockitoSugar {

  val printer: Printer = Printer.noSpaces.copy(dropNullValues = true)

  val config: GoCardlessConfiguration = GoCardlessConfiguration(
    "https://api-sandbox.gocardless.com/",
    Properties.envOrElse("GOCARDLESS_ACCESS_TOKEN", "my_gocardless_access_token")
  )

  val unproccessableEntityError: GoCardlessApiError = GoCardlessApiError(
    GoCardlessError(
      "validation_failed",
      422,
      "Validation failed",
      "https://developer.gocardless.com/#validation_failed",
      UUID.fromString("dd50eaaf-8213-48fe-90d6-5466872efbc4"),
      Seq(
        Validation("must be a number", "branch_code", "/customer_bank_accounts/branch_code".some),
        Validation(
          "is the wrong length (should be 8 characters)",
          "branch_code",
          "/customer_bank_accounts/branch_code".some
        )
      )
    )
  )

  val conflictError: GoCardlessApiError = GoCardlessApiError(
    GoCardlessError(
      "validation_failed",
      409,
      "Bank account already exists",
      "https://developer.gocardless.com/#bank_account_exists",
      UUID.fromString("bd271b37-a2f5-47c8-b461-040dfe0e9cb1"),
      Seq(
        Default(
          "Bank account already exists",
          "bank_account_exists",
          Map("creditor_bank_account" -> "BA123".asJson).some
        )
      )
    )
  )

  val badRequestError: GoCardlessApiError = GoCardlessApiError(
    GoCardlessError(
      "invalid_api_usage",
      400,
      "Invalid document structure",
      "https://developer.gocardless.com/#invalid_document_structure",
      UUID.fromString("bd271b37-a2f5-47c8-b461-040dfe0e9cb1"),
      Seq(Default("Invalid document structure", "invalid_document_structure"))
    )
  )

}
