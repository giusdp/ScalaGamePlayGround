name := "untitled"

version := "0.1"

scalaVersion := "2.12.8"

scalacOptions += "-Ypartial-unification"

libraryDependencies += "org.typelevel" %% "cats-core" % "1.3.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "1.3.0"

libraryDependencies ++= Seq(
  "net.debasishg" %% "redisclient" % "3.9"
)

libraryDependencies ++= {
  val version = "3.2.0"
  val os = "linux" // TODO: Change to "linux" or "macos" if necessary

  Seq(
    "lwjgl",
    "lwjgl-glfw",
    "lwjgl-opengl"
    // TODO: Add more modules here
  ).flatMap {
    module => {
      Seq(
        "org.lwjgl" % module % version,
        "org.lwjgl" % module % version classifier s"natives-$os"
      )
    }
  }
}