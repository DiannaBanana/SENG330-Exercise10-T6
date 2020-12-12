name := "fall20ex10_starter"

version := "2.8.x"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.3"


resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  guice,
  javaJdbc,
  javaJpa,
  evolutions,

  "com.adrianhurt" %% "play-bootstrap" % "1.6.1-P28-B4",
  "org.hibernate" % "hibernate-core" % "5.4.9.Final",
  "com.h2database" % "h2" % "1.4.192",

  "org.junit.platform" % "junit-platform-runner" % "1.0.0-M3" % "test",
  "org.junit.jupiter" % "junit-jupiter-engine" % "5.0.0-M3" % "test",
  "org.junit.vintage" % "junit-vintage-engine" % "4.12.0-M3" % "test",

  "com.novocode" % "junit-interface" % "0.11" % Test



)

testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a"))

testFrameworks += TestFrameworks.JUnit
