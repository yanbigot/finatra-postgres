name := "finatra-postgres"

version := "1.0"

scalaVersion := "2.11.11"

resolvers += Resolver.sonatypeRepo("snapshots")

lazy val versions = new {
  val finatra = "18.10.0"
  val logback = "1.1.7"
  val guice = "4.0"
}
libraryDependencies += "com.typesafe" % "config" % "1.3.2"
libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-http" % versions.finatra,
  "ch.qos.logback" % "logback-classic" % versions.logback,

  "com.twitter" %% "finatra-http" % versions.finatra % "test",
  "com.twitter" %% "finatra-jackson" % versions.finatra % "test",
  "com.twitter" %% "inject-server" % versions.finatra % "test",
  "com.twitter" %% "inject-app" % versions.finatra % "test",
  "com.twitter" %% "inject-core" % versions.finatra % "test",
  "com.twitter" %% "inject-modules" % versions.finatra % "test",
  "com.google.inject.extensions" % "guice-testlib" % versions.guice % "test",

  "com.twitter" %% "finatra-http" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "finatra-jackson" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-server" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-app" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-core" % versions.finatra % "test" classifier "tests",
  "com.twitter" %% "inject-modules" % versions.finatra % "test" classifier "tests",

  "org.mockito" % "mockito-core" % "1.9.5" % "test",
  "org.scalacheck" %% "scalacheck" % "1.13.4" % "test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.specs2" %% "specs2-mock" % "2.4.17" % "test",

  "io.github.finagle" %% "finagle-postgres" % "0.7.0",
  "net.liftweb" %% "lift-json" % "3.3.0",

  "ru.yandex.qatools.embed" % "postgresql-embedded" % "2.10" % Test,

  "org.postgresql" % "postgresql" % "42.2.5"
)