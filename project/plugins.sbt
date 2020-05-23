resolvers += "Bintray sbt plugin releases" at "https://dl.bintray.com/sbt/sbt-plugin-releases/"
resolvers += Resolver.typesafeIvyRepo("releases")
addSbtPlugin("org.scoverage"            %% "sbt-scoverage" % "1.6.1")
addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat"  % "0.1.3")
