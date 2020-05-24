package com.smur89.gocardless.models

import com.smur89.gocardless.{BaseSpec, Fixture}
import io.circe.syntax._

import scala.io.Source

class GoCardlessModelParseTest extends BaseSpec {

  "GoCardless Client" when {
    "Serialising/Deserialising" should {
      "deserialise unprocessable entity" in new Fixture {
        val json = Source.fromResource("unprocessable_entity.json").getLines.mkString
        unproccessableEntityError.asJson.noSpaces shouldBe json
      }

      "deserialise conflict error" in new Fixture {
        val json = Source.fromResource("conflict.json").getLines.mkString
        conflictError.asJson.noSpaces shouldBe json
      }

      "deserialise Bad Request" in new Fixture {
        val json = Source.fromResource("bad_request.json").getLines.mkString
        badRequestError.asJson.noSpaces shouldBe json
      }
    }
  }
}
