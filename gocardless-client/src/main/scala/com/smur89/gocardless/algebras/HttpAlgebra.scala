package com.smur89.gocardless.algebras

import com.smur89.gocardless.models.api.{GoCardlessApiEntity, GoCardlessResponse}

import scala.language.higherKinds

trait HttpAlgebra[F[_], A] {
  def getRequest:      F[A]
  def postRequest:     GoCardlessApiEntity => F[A]
  def putRequest:      GoCardlessApiEntity => F[A]
  def deleteRequest:   GoCardlessApiEntity => F[A]
  def responseHandler: A => F[GoCardlessResponse]
}
