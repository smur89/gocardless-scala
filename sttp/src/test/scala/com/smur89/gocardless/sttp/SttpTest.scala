package com.smur89.gocardless.sttp

import monix.execution.Scheduler
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.concurrent.duration.Duration

class SttpTest extends AnyWordSpec with Matchers {

  "Sttp Client" when {
    implicit val s = Scheduler(scala.concurrent.ExecutionContext.global)
    implicit val d = Duration.Inf

    "Sending GET" in new Fixture {
      val resp = mockHttpSender.getRequest("customers/CU0009TBQMFSWQ").value.runSyncUnsafe(d)
      println(resp)
      println(resp)
      resp.map(res => {
        println(res.body)
        res shouldBe 200
      })
    }

    "Sending PUT" ignore {}

    "Sending POST" ignore {}

    "Sending DELETE" ignore {}
  }
}
