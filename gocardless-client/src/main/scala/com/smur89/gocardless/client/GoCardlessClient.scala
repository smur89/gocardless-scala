package com.smur89.gocardless.client

import com.smur89.gocardless.algebras.{ClientAlgebra, HttpAlgebra}
import com.smur89.gocardless.models.api.Customer

import cats.implicits.toFlatMapOps
import cats.effect.Async

import scala.language.higherKinds

class GoCardlessClient[F[_], A](http: HttpAlgebra[F, A])(implicit F: Async[F]) extends ClientAlgebra[F] {

  val customersRootPath = "/customers"

  override def createCustomer(req: Customer): F[String] =
    http.postRequest(customersRootPath)(req).flatMap(responseHandler)

  override def listCustomers: F[Unit] = ???

  override def getCustomer(id: String): F[Unit] = ???

  override def updateCustomer(id: String, customer: Customer): F[Unit] = ???

  override def removeCustomer(id: String): F[Unit] = ???

  def responseHandler: A => F[String] = http.responseHandler
}
