package com.smur89.gocardless.client

import com.smur89.gocardless.algebras.{ClientAlgebra, HttpAlgebra}
import com.smur89.gocardless.models.GoCardlessClientError
import com.smur89.gocardless.models.api.{Creditor, CreditorBankAccount, Customer}

import cats.ApplicativeError
import cats.implicits.toFlatMapOps
import cats.effect.Async

import scala.language.higherKinds

class GoCardlessClient[F[_], A](http: HttpAlgebra[F, A])(implicit
  F:                                  Async[F],
  FE:                                 ApplicativeError[F, GoCardlessClientError]
) extends ClientAlgebra[F] {

  override def createCustomer(req: Customer): F[Unit] =
    http.postRequest(req).flatMap(responseHandler)

  override def listCustomers: F[Unit] = ???

  override def getCustomer(id: String): F[Unit] = ???

  override def updateCustomer(id: String, customer: Customer): F[Unit] = ???

  override def removeCustomer(id: String): F[Unit] = ???

  override def createCreditor(customer: Creditor): F[Unit] = ???

  override def listCreditors: F[Unit] = ???

  override def getCreditor(id: String): F[Unit] = ???

  override def updateCreditor(id: String, customer: Creditor): F[Unit] = ???

  override def createCreditorsBankAccount(creditorBankAccount: CreditorBankAccount): F[Unit] = ???

  override def listCreditorBankAccounts: F[Unit] = ???

  override def getSingleCreditorsBankAccount(id: String): F[Unit] = ???

  override def disableCreditorsBankAccount(id: String, customer: CreditorBankAccount): F[Unit] = ???

  override def listCurrencyExchangeRates: F[Unit] = ???

  def responseHandler: A => F[Unit] =
    http.responseHandler.andThen(_.flatMap {
      case resp => F.pure(resp)
      case error: GoCardlessClientError[_] => FE.raiseError[Unit](error)
    })
}
