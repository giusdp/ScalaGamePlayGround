name := "SGExp"

version := "0.1"

scalaVersion := "2.13.1"

// https://mvnrepository.com/artifact/org.l33tlabs.twl/pngdecoder
libraryDependencies += "org.l33tlabs.twl" % "pngdecoder" % "1.0"

// https://mvnrepository.com/artifact/org.joml/joml
libraryDependencies += "org.joml" % "joml" % "1.9.15"

// https://mvnrepository.com/artifact/com.badlogicgames.ashley/ashley
libraryDependencies += "com.badlogicgames.ashley" % "ashley" % "1.7.3"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.2.0"

libraryDependencies ++= {
  val version = "3.2.3"
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