name := """logbase-api"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "io.logbase" % "logbase-quick" % "1.0-SNAPSHOT",
  "com.objectdb" % "objectdb" % "2.5.7",
  "org.antlr" % "antlr4-runtime" % "4.3",
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)

resolvers ++= Seq(
	Resolver.mavenLocal,
	"ObjectDB Repository" at "http://m2.objectdb.com"
)