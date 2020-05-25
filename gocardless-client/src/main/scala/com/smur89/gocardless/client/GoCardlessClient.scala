package com.smur89.gocardless.client

import com.smur89.gocardless.algebras.{ClientAlgebra, HttpAlgebra}
import com.smur89.gocardless.models.api.{Customer, GoCardlessError}

import cats.ApplicativeError
import cats.implicits.toFlatMapOps
import cats.effect.Async

import scala.language.higherKinds

class GoCardlessClient[F[_], A](http: HttpAlgebra[F, A])(implicit
  F:                                  Async[F],
  FE:                                 ApplicativeError[F, GoCardlessError]
) extends ClientAlgebra[F] {

  override def createCustomer(req: Customer): F[Unit] =
    http.postRequest(req).flatMap(responseHandler)

  override def listCustomers: F[Unit] = ???

  override def getCustomer(id: String): F[Unit] = ???

  override def updateCustomer(id: String, customer: Customer): F[Unit] = ???

  override def removeCustomer(id: String): F[Unit] = ???

  def responseHandler: A => F[Unit] =
    http.responseHandler.andThen(_.flatMap {
      case error: GoCardlessError => FE.raiseError[Unit](error)
      case _ => F.unit
    })
}
