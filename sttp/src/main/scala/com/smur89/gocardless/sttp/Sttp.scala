package com.smur89.gocardless.sttp

import com.smur89.gocardless.algebras.HttpAlgebra
import com.smur89.gocardless.models._
import com.smur89.gocardless.models.api.{GcError, GenericError, GoCardlessApiEntity, ServiceUnavailable}
import io.circe.syntax._
import sttp.client._
import sttp.client.asynchttpclient.WebSocketHandler
import sttp.model.{Header, StatusCode}

import cats.mtl.ApplicativeHandle
import cats.effect.{Async, ContextShift}

import scala.language.higherKinds

class Sttp[F[_]: ContextShift](config: GoCardlessConfiguration)(implicit
  F:        Async[F],
  AH:       ApplicativeHandle[F, GcError],
  httpImpl: SttpBackend[F, Nothing, WebSocketHandler]
) extends HttpAlgebra[F, Response[Either[String, String]]] {

  private val headers = Seq(
    Header("GoCardless-Version", "2015-07-06"),
    Header("Content-Type", "application/json"),
    Header("Content-Length", "3495")
  )
  private val gocardlessRequest = basicRequest.auth
    .bearer(config.accessToken)
    .headers(headers: _*)

  override def getRequest(url: String): F[Response[Either[String, String]]] = {
    val value = uri"https://google.com"
//    val value = uri"${config.url}/$url"
    gocardlessRequest.get(value).send()
  }

  override def postRequest(url: String): GoCardlessApiEntity => F[Response[Either[String, String]]] = { request =>
    gocardlessRequest.body(request.asJson.noSpaces).post(uri"${config.url}/$url").send()
  }

  override def putRequest(url: String): GoCardlessApiEntity => F[Response[Either[String, String]]] = { request =>
    gocardlessRequest.body(request.asJson.noSpaces).put(uri"${config.url}/$url/${request.id}").send()
  }

  override def deleteRequest(url: String): String => F[Response[Either[String, String]]] = { id =>
    gocardlessRequest.delete(uri"${config.url}/$url/$id").send()
  }

  override def responseHandler: Response[Either[String, String]] => F[String] =
    ((_: Response[Either[String, String]]) match {
      case Response(body, StatusCode.Ok, _, _, _)              => body.fold(F.pure, error => AH.raise(GenericError(error)))
      case Response(body, StatusCode.Conflict, _, _, _)        => body.fold(F.pure, error => AH.raise(GenericError(error)))
      case Response(_, StatusCode.ServiceUnavailable, _, _, _) => AH.raise(ServiceUnavailable("Service unavailable"))
      case _                                                   => AH.raise(ServiceUnavailable("Service unavailable???"))
    })
}
