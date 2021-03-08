name := "http4s-doobie"

version := "0.1"

scalaVersion := "2.13.2"

val http4sVersion = "0.21.15"
val circeVersion = "0.13.0"
val h2Version = "1.4.200"
val slf4jVersion = "1.7.30"
val doobieVersion = "0.10.0"
val catsVersion = "2.3.1"
val cfgVersion = "1.4.1"

// Only necessary for SNAPSHOT releases
resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-yaml" % circeVersion,
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.typelevel" %% "cats-effect" % catsVersion,
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-h2" % doobieVersion, // H2 driver 1.4.200 + type mappings.
  "org.tpolecat" %% "doobie-hikari" % doobieVersion, // HikariCP transactor.
  "com.h2database" % "h2" % h2Version,
  "org.slf4j" % "slf4j-api" % slf4jVersion,
  "org.slf4j" % "slf4j-simple" % slf4jVersion,
  "com.typesafe" % "config" % cfgVersion
)
