name := "SGExp"

version := "0.1"

scalaVersion := "2.12.8"

// https://mvnrepository.com/artifact/org.scala-lang/scala-reflect
libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.12.8"


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