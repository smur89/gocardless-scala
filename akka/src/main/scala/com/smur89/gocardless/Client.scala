package com.smur89.gocardless

import akka.http.scaladsl.model.HttpResponse
import com.smur89.gocardless.client.GoCardlessClient
import com.smur89.gocardless.models.api.GoCardlessError

import cats.ApplicativeError
import cats.effect.Async

import scala.language.higherKinds

class Client[F[_]](http: AkkaHttp[F])(implicit F: Async[F], FE: ApplicativeError[F, GoCardlessError])
  extends GoCardlessClient[F, HttpResponse](http)
