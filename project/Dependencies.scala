import sbt._

object Dependencies {

  object versions {
    val cats             = "2.1.1"
    val `cats-mtl`       = "0.7.0"
    val `cats-effect`    = "2.1.3"
    val chimney          = "0.5.2"
    val circe            = "0.13.0"
    val `kind-projector` = "0.10.3"
    val mockito          = "1.14.7"
    val monix            = "3.2.2"
    val newtype          = "0.4.4"
    val `paradise-macro` = "2.1.1"
    val scalatest        = "3.2.0"
    val sttp             = "2.2.0"
    val tagless          = "0.11"
  }

  val cats: Seq[ModuleID] = Seq(
    "org.typelevel" %% "cats-core"     % versions.cats,
    "org.typelevel" %% "cats-mtl-core" % versions.`cats-mtl`,
    "org.typelevel" %% "cats-effect"   % versions.`cats-effect`
  )

  val chimney: Seq[ModuleID] = Seq("io.scalaland" %% "chimney" % versions.chimney)

  val circe: Seq[ModuleID] = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-generic-extras",
    "io.circe" %% "circe-parser"
  ).map(_ % versions.circe)

  val `kind-projector`: ModuleID = "org.typelevel" %% "kind-projector" % versions.`kind-projector`

  val monix: Seq[ModuleID] = Seq(
    "io.monix" %% "monix"           % versions.monix,
    "io.monix" %% "monix-eval"      % versions.monix,
    "io.monix" %% "monix-execution" % versions.monix
  )

  val newtype = Seq("io.estatico" %% "newtype" % versions.newtype)

  val `paradise-macro`: ModuleID = "org.scalamacros" % "paradise" % versions.`paradise-macro` cross CrossVersion.full

  val sttp: Seq[ModuleID] = Seq(
    "com.softwaremill.sttp.client" %% "core"                            % versions.sttp,
    "com.softwaremill.sttp.client" %% "async-http-client-backend-monix" % versions.sttp
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
