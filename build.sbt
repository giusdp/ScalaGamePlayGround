name := "SGExp"

version := "0.1"

scalaVersion := "2.13.0"

// https://mvnrepository.com/artifact/org.scala-lang/scala-reflect
libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.12.8"

libraryDependencies += "org.json4s" %% "json4s-native" % "3.6.6"

// https://mvnrepository.com/artifact/org.l33tlabs.twl/pngdecoder
libraryDependencies += "org.l33tlabs.twl" % "pngdecoder" % "1.0"

// https://mvnrepository.com/artifact/org.joml/joml
libraryDependencies += "org.joml" % "joml" % "1.9.15"

// https://mvnrepository.com/artifact/com.badlogicgames.ashley/ashley
libraryDependencies += "com.badlogicgames.ashley" % "ashley" % "1.7.3"

libraryDependencies ++= {
  val version = "3.2.0"
  val os = "linux"

  Seq(
    "lwjgl",
    "lwjgl-glfw",
    "lwjgl-opengl"
  ).flatMap {
    module => {
      Seq(
        "org.lwjgl" % module % version,
        "org.lwjgl" % module % version classifier s"natives-$os"
      )
    }
  }
}