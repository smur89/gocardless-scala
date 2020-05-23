import sbt._

object Dependencies {

  object versions {
    val akka          = "10.1.12"
    val akkaCirce     = "1.32.0"
    val cats          = "2.1.1"
    val `cats-effect` = "2.1.3"
    val chimney       = "0.5.2"
    val circe         = "0.13.0"
    val mockito       = "1.14.2"
    val pureconfig    = "0.12.3"
    val scalatest     = "3.1.2"
    val tagless       = "0.11"
  }

  val akka = Seq(
    "com.typesafe.akka" %% "akka-http"       % versions.akka,
    "de.heikoseeberger" %% "akka-http-circe" % versions.akkaCirce
  )

  val cats: Seq[ModuleID] = Seq(
    "org.typelevel" %% "cats-core"   % versions.cats,
    "org.typelevel" %% "cats-effect" % versions.`cats-effect`
  )

  val chimney: Seq[ModuleID] = Seq("io.scalaland" %% "chimney" % versions.chimney)

  val circe: Seq[ModuleID] = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-generic-extras",
    "io.circe" %% "circe-parser"
  ).map(_ % versions.circe)

  val paradiseMacro: ModuleID = "org.typelevel" %% "kind-projector" % "0.10.3"

  val pureconfig: Seq[ModuleID] = Seq(
    "com.github.pureconfig" %% "pureconfig"         % versions.pureconfig,
    "com.github.pureconfig" %% "pureconfig-generic" % versions.pureconfig
  )

  val tagless: Seq[ModuleID] = Seq(
    "org.typelevel" %% "cats-tagless-core"   % versions.tagless,
    "org.typelevel" %% "cats-tagless-macros" % versions.tagless
  )

  val tests: Seq[ModuleID] = Seq(
    "org.mockito"   %% "mockito-scala" % versions.mockito,
    "org.scalatest" %% "scalatest"     % versions.scalatest
  ).map(_ % Test)
}
