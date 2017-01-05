name := "smartumpro-test"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  ws,
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "2.0.0",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  "org.json4s" %% "json4s-jackson" % "3.5.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test"
)