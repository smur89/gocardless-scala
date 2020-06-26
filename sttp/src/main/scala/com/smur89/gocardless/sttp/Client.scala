package com.smur89.gocardless.sttp

import com.smur89.gocardless.client.GoCardlessClient
import sttp.client.Response

import cats.effect.Async

import scala.language.higherKinds

class Client[F[_]](http: Sttp[F])(implicit F: Async[F])
  extends GoCardlessClient[F, Response[Either[String, String]]](http)
