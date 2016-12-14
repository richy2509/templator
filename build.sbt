name := "templator"

version := "1.0"

scalaVersion := "2.12.1"

mainClass := Some("com.richy2509.templator.Main")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

libraryDependencies += "org.yaml" % "snakeyaml" % "1.8"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"

// https://mvnrepository.com/artifact/org.freemarker/freemarker
libraryDependencies += "org.freemarker" % "freemarker" % "2.3.25-incubating"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.play" % "play-json_2.12" % "2.6.0-M1"