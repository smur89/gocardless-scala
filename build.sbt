import Settings._
import com.typesafe.config.ConfigFactory

Global / cancelable   := true
Global / version      := ConfigFactory.parseFile(new File("version.sh")).getString("VERSION")
Global / scalaVersion := "2.12.10"

val domain = "gocardless"

lazy val root = Project(id = domain, base = file(".")).aggregate(`gocardless-client`, sttp)

lazy val `gocardless-client` =
  Project(id = "gocardless-client", base = file("gocardless-client"))
    .settings(name := s"$domain-client")
    .deps(Dependencies)(
      _.cats,
      _.circe,
      _.newtype,
      _.tagless,
      _.tests,
      _ => Seq(compilerPlugin(Dependencies.`kind-projector`), compilerPlugin(Dependencies.`paradise-macro`))
    )

lazy val sttp =
  Project(id = "sttp", base = file("sttp"))
    .settings(name := s"$domain-sttp")
    .deps(Dependencies)(
      _.cats,
      _.circe,
      _.monix,
      _.sttp,
      _.tests,
      _ => Seq(compilerPlugin(Dependencies.`kind-projector`), compilerPlugin(Dependencies.`paradise-macro`))
    )
    .dependsOn(`gocardless-client`)
