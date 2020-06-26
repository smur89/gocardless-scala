resolvers += Resolver.typesafeIvyRepo("releases")
resolvers += Resolver.sonatypeRepo("releases")

val paradiseMacro = "2.1.1"
val scoverage     = "1.6.1"
val tpolecat      = "0.1.11"

addSbtPlugin("org.scoverage"            %% "sbt-scoverage" % scoverage)
addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat"  % tpolecat)
