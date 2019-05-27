package game_engine

import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.GLFWKeyCallbackI
import simulation.MovementStateMachine

object Input {

  def setupCallbacks(window: Long): Unit = {
    val fn : GLFWKeyCallbackI =
      (window: Long, key: Int, scancode: Int, action: Int, mods: Int) =>
        action match {

          case GLFW_PRESS =>
            key match {
              case GLFW_KEY_Q => glfwSetWindowShouldClose(window, true)

              case _ => MovementStateMachine.update(key)
            }

          case GLFW_RELEASE => MovementStateMachine.update(GLFW_RELEASE)

          case _ =>  // GLFW_REPEAT
        }

    glfwSetKeyCallback(window, fn)
  }

  def tickInput(): Unit = {
    glfwPollEvents()
  }

}
