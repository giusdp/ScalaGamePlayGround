package game_engine

import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.{GLFWKeyCallback, GLFWKeyCallbackI}

object Input {

  def setupCallbacks(window: Long): GLFWKeyCallback = {
    val fn : GLFWKeyCallbackI =
      (window: Long, key: Int, scancode: Int, action: Int, mods: Int) =>
        glfwSetWindowShouldClose(window, true)

    glfwSetKeyCallback(window, fn)
  }

  def tickInput(): Unit = {
    glfwPollEvents()
  }

}
