import game_engine.{Engine, Input}
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryUtil._

import scala.annotation.tailrec


object GameLauncher {

  def main(args : Array[String]): Unit = {
    Engine.run()
  }

}
