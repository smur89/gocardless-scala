package com.smur89.gocardless

import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

abstract class BaseSpec extends AnyWordSpec with BeforeAndAfter with OptionValues with EitherValues with Matchers

abstract class ClientSpec extends AnyWordSpec with BeforeAndAfter with BeforeAndAfterAll
