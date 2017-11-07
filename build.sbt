// Some common settings of whole project.
lazy val commonSettings = Seq(
  organization := "org.dsngroup",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.4",
  javacOptions := Seq("-source", "1.8", "-target", "1.8")
)


//lazy val orcarActor = (project in file("orcar-actor"))
lazy val orcarActor = Project("orcar-actor", file("orcar-actor"))
  .settings(
    commonSettings,
    name := "orcar-actor",
    description := "The actor interfaces for implementation.",
    libraryDependencies ++= Seq(
      "com.google.code.gson" % "gson" % "2.8.2"
    )
  )

lazy val orcarConnector = Project("orcar-connector", file("orcar-connector"))
  .settings(
    commonSettings,
    name := "orcar-connector",
    description := "The low-level device connector module."
  )

lazy val orcarEventCache = Project("orcar-eventcache", file("orcar-eventcache"))
  .settings(
    commonSettings,
    name := "orcar-eventcache",
    description := "The event cache for devices or cloudlets."
  )

lazy val orcarGpio = Project("orcar-gpio", file("orcar-gpio"))
  .settings(
    commonSettings,
    name := "orcar-gpio",
    description := "The low-level gpio api for devices."
  )

lazy val orcarRuntime = Project("orcar-runtime", file("orcar-runtime"))
  .dependsOn(
    orcarActor,
    orcarGpio
  )
  .settings(
    commonSettings,
    name := "orcar-runtime",
    description := "The low-level runtime for device.",
    libraryDependencies ++= Seq(
      "org.slf4j" % "slf4j-api" % "1.7.25",
      "org.slf4j" % "slf4j-jdk14" % "1.7.25",
      "com.novocode" % "junit-interface" % "0.11" % Test
    ),
    mainClass in (Compile, run) := Some("org.dsngroup.orcar.runtime.RuntimeService"),
    testOptions += Tests.Argument(TestFrameworks.JUnit)
  )

lazy val orcarSample = Project("orcar-sample", file("orcar-sample"))
  .dependsOn(
    orcarGpio,
    orcarActor
  )
  .settings(
    commonSettings,
    name := "orcar-sample",
    description := "Sample program which implements actor interfaces."
  )

// Controller side
lazy val orcarApiServer = Project("orcar-apiserver", file("orcar-apiserver"))
  .settings(
    commonSettings,
    name := "orcar-apiserver",
    description := "API server in cloudlets, master controller to handle the incoming api requests",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % "10.0.10",
      "com.typesafe.akka" %% "akka-stream" % "2.5.4",
      "com.typesafe.akka" %% "akka-actor"  % "2.5.4"
    ),
    mainClass in (Compile, run) := Some("org.dsngroup.orcar.apiserver.APIServer")
  )

lazy val orcarGateway = Project("orcar-gateway", file("orcar-gateway"))
  .settings(
    commonSettings,
    name := "orcar-gateway",
    description := "South bridge gateway proxy."
  )

lazy val orcarCLI = Project("orcar-cli", file("orcar-cli"))
  .settings(
    commonSettings,
    name := "orcar-cli",
    description := "Command line tools which interops with API server",
    libraryDependencies ++= Seq(
      "com.github.scopt" %% "scopt" % "3.7.0"
    )
  )
