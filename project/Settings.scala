import sbt.Keys._
import sbt._

object Settings {
  implicit class ProjectSettings(project: Project) {
    def deps[A](a: A)(deps: (A => Seq[ModuleID])*): Project =
      project.settings(
        libraryDependencies ++= deps.flatMap(_.apply(a))
      )
  }
}
