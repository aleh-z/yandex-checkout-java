name := "yandex-checkout-java"
description := "An asynchronous Java client for Yandex.Checkout API"

version := "0.9.0"

scalaVersion := "2.12.7"
scalacOptions += "-target:jvm-1.8"
javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-parameters")

parallelExecution in Test := false

libraryDependencies ++= {
  val jacksonVersion = "2.9.7"
  val akkaVersion = "2.5.18"

  Seq(
    "org.asynchttpclient" % "async-http-client" % "2.6.0",

    "com.nurkiewicz.asyncretry" % "asyncretry" % "0.0.7",

    "org.javamoney" % "moneta" % "1.3",

    "com.fasterxml.jackson.core" % "jackson-core" % jacksonVersion,
    "com.fasterxml.jackson.core" % "jackson-annotations" % jacksonVersion,
    "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion,
    "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % jacksonVersion,
    "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % jacksonVersion,
    "com.fasterxml.jackson.module" % "jackson-module-parameter-names" % jacksonVersion,

    "org.zalando" % "jackson-datatype-money" % "1.1.0",

    "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    "com.typesafe.akka" %% "akka-actor" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-http" % "10.1.5" % Test,
    "org.scala-lang.modules" %% "scala-java8-compat" % "0.9.0" % Test
  )
}

