package com.smur89.gocardless.algebras

import com.smur89.gocardless.models.api.GoCardlessApiEntity

import scala.language.higherKinds

trait HttpAlgebra[F[_], A] {
  def getRequest(url:    String): F[A]
  def postRequest(url:   String): GoCardlessApiEntity => F[A]
  def putRequest(url:    String): GoCardlessApiEntity => F[A]
  def deleteRequest(url: String): String => F[A]
  def responseHandler: A => F[String]
}
