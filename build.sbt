import com.typesafe.config.ConfigFactory

Global / cancelable := true
Global / version    := ConfigFactory.parseFile(new File("version.sh")).getString("VERSION")

val domain = "gocardless"

lazy val root = Project(id = domain, base = file(".")).aggregate(`gocardless-client`)

lazy val `gocardless-client` =
  Project(id = "gocardless-client", base = file("gocardless-client"))
    .settings(
      scalaVersion := "2.12.10",
      name         := "gocardless-client",
      libraryDependencies ++= Seq(
        Dependencies.akka,
        Dependencies.cats,
        Dependencies.circe,
        Dependencies.pureconfig,
        Dependencies.tagless,
        Dependencies.tests,
        Seq(compilerPlugin(Dependencies.paradiseMacro))
      ).flatten
    )
