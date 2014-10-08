name := """logbase-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "io.logbase" % "logbase-quick" % "1.0-SNAPSHOT",
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)

resolvers += Resolver.mavenLocal
