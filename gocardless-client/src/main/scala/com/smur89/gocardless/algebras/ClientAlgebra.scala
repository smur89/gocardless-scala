package com.smur89.gocardless.algebras

import com.smur89.gocardless.models.api.{Creditor, CreditorBankAccount, Customer}

import cats.tagless.{Derive, FunctorK}

import scala.language.higherKinds

trait ClientAlgebra[F[_]] {
  // Creditor
  def createCreditor(customer: Creditor): F[Unit]
  def listCreditors: F[Unit]
  def getCreditor(id:    String): F[Unit]
  def updateCreditor(id: String, customer: Creditor): F[Unit]

  // Creditors Bank Accounts
  def createCreditorsBankAccount(creditorBankAccount: CreditorBankAccount): F[Unit]
  def listCreditorBankAccounts: F[Unit]
  def getSingleCreditorsBankAccount(id: String): F[Unit]
  def disableCreditorsBankAccount(id:   String, customer: CreditorBankAccount): F[Unit]

  // Currency Exchange Rates
  def listCurrencyExchangeRates: F[Unit]

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
