import com.typesafe.config.ConfigFactory
import Settings._

Global / cancelable   := true
Global / version      := ConfigFactory.parseFile(new File("version.sh")).getString("VERSION")
Global / scalaVersion := "2.12.10"

val domain = "gocardless"

lazy val root = Project(id = domain, base = file(".")).aggregate(`gocardless-client`, akka)

lazy val `gocardless-client` =
  Project(id = "gocardless-client", base = file("gocardless-client"))
    .settings(name := s"$domain-client")
    .deps(Dependencies)(_.akka, _.cats, _.circe, _.tagless, _.tests)

lazy val akka =
  Project(id = "akka", base = file("akka"))
    .settings(name := s"$domain-akka")
    .deps(Dependencies)(_.cats, _.circe, _.monix, _.tests, _ => Seq(compilerPlugin(Dependencies.paradiseMacro)))
    .dependsOn(`gocardless-client`)
