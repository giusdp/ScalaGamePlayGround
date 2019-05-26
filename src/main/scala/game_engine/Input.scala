package game_engine

import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.GLFWKeyCallbackI
import simulation.InputAdapter._

object Input {

  def setupCallbacks(window: Long): Unit = {
    val fn : GLFWKeyCallbackI =
      (window: Long, key: Int, scancode: Int, action: Int, mods: Int) =>
        action match {

          case GLFW_PRESS =>
            key match {
              case GLFW_KEY_Q => glfwSetWindowShouldClose(window, true)
              case GLFW_KEY_A => pressedA()
              case GLFW_KEY_D => pressedD()
              case GLFW_KEY_W => pressedW()
              case GLFW_KEY_S => pressedS()
              case GLFW_KEY_LEFT_SHIFT => pressedShift()
              case _ =>
            }

          case GLFW_RELEASE => releasedKey()

          case _ =>  // GLFW_REPEAT
        }

    glfwSetKeyCallback(window, fn)
  }

  def tickInput(): Unit = {
    glfwPollEvents()
  }

}
