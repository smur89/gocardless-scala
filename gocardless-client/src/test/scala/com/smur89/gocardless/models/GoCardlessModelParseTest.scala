package com.smur89.gocardless.models

import com.smur89.gocardless.{BaseSpec, Fixture}
import io.circe.syntax._

import scala.io.Source

class GoCardlessModelParseTest extends BaseSpec {

  "GoCardless Client" when {
    "Serialising/Deserialising" should {
      "deserialise unprocessable entity" in new Fixture {
        val json = Source.fromResource("unprocessable_entity.json").getLines.mkString
        json shouldBe unproccessableEntityError.asJson.printWith(printer)
      }

      "deserialise conflict error" in new Fixture {
        val json = Source.fromResource("conflict.json").getLines.mkString
        json shouldBe conflictError.asJson.printWith(printer)
      }

      "deserialise Bad Request" in new Fixture {
        val json = Source.fromResource("bad_request.json").getLines.mkString
        json shouldBe badRequestError.asJson.printWith(printer)
      }
    }
  }
}
