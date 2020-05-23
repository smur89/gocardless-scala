package com.smur89.gocardless.client

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import com.smur89.gocardless.algebras.HttpAlgebra
import com.smur89.gocardless.models._
import com.smur89.gocardless.models.api.GoCardlessApiEntity
import io.circe.syntax._

import cats.~>
import cats.effect.{Async, ContextShift}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.higherKinds

class AkkaHttp[F[_]: ContextShift](config: GoCardlessConfiguration)(implicit
  F:            Async[F],
  system:       ActorSystem,
  materializer: Materializer
) extends HttpAlgebra[F, HttpResponse] {

  private val functionK: Future ~> F = λ[Future ~> F](f => Async.fromFuture(F.pure(f)))

  override def getRequest: F[HttpResponse] = ???

  override def postRequest: GoCardlessApiEntity => F[HttpResponse] = { request =>
    def createRequest(batch: GoCardlessApiEntity) =
      HttpRequest(
        method = HttpMethods.POST,
        uri = config.url,
        entity = HttpEntity(batch.asJson.noSpaces).withContentType(ContentTypes.`application/json`)
      )
    functionK(Http(system).singleRequest(createRequest(request)))
  }

  override def putRequest: GoCardlessApiEntity => F[HttpResponse] = ???

  override def deleteRequest: GoCardlessApiEntity => F[HttpResponse] = ???

  override def responseHandler: HttpResponse => F[GoCardlessResponse] = ???

  override def responseHandler: HttpResponse => F[GoCardlessResponse] =
    ((_: HttpResponse) match {
      case HttpResponse(StatusCodes.OK, _, entity, _)         => functionK(Unmarshal(entity).to[GoCardlessResponse])
      case HttpResponse(StatusCodes.Conflict, _, entity, _)   => functionK(Unmarshal(entity).to[Conflict])
      case HttpResponse(StatusCodes.BadRequest, _, entity, _) => functionK(Unmarshal(entity).to[BadRequest])
      case HttpResponse(StatusCodes.UnprocessableEntity, _, entity, _) =>
        functionK(Unmarshal(entity).to[UnprocessableEntity])
      case HttpResponse(StatusCodes.BadGateway, _, _, _)     => F.pure(ServerError(ErrorMessages.BadGateway))
      case HttpResponse(StatusCodes.GatewayTimeout, _, _, _) => F.pure(ServerError(ErrorMessages.GatewayTimeout))
      case HttpResponse(StatusCodes.ServiceUnavailable, _, _, _) =>
        F.pure(ServiceUnavailable(ErrorMessages.ServiceUnavailable))
      case _ => F.pure(ServiceUnavailable(ErrorMessages.ServiceUnavailable))
    })
}
