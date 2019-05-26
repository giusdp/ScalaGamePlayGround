import game_engine.InputReceiver
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryUtil._

import scala.annotation.tailrec


object GameLauncher {

  def main(args : Array[String]): Unit = {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set()

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if ( !glfwInit() )
      throw new IllegalStateException("Unable to initialize GLFW")

    val window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL)
    if (window == NULL) throw new RuntimeException("Failed to create the GLFW window")

    InputReceiver.setupCallbacks(window)

    game_loop(window)

  }

  @tailrec
  def game_loop(window: Long): Unit = {
    InputReceiver.tickInput()

    if (glfwWindowShouldClose(window)) return

    game_loop(window)
  }
}
