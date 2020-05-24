package com.smur89.gocardless.algebras

import com.smur89.gocardless.models.api.Customer

import cats.tagless.{Derive, FunctorK}

import scala.language.higherKinds

trait ClientAlgebra[F[_]] {
  // Customer
  def createCustomer(customer: Customer): F[Unit]
  def listCustomers: F[Unit]
  def getCustomer(id:    String): F[Unit]
  def updateCustomer(id: String, customer: Customer): F[Unit]
  def removeCustomer(id: String): F[Unit]
}

object ClientAlgebra {
  implicit val functorK: FunctorK[ClientAlgebra] =
    Derive.functorK[ClientAlgebra]
}
