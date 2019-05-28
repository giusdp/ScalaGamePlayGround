package game_engine

import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.GLFWKeyCallbackI
import simulation.MovementStateMachine

object Input {

  def setupCallbacks(window: Long, stateMachine: MovementStateMachine): Unit = {
    val fn : GLFWKeyCallbackI =
      (window: Long, key: Int, scancode: Int, action: Int, mods: Int) =>
        action match {

          case GLFW_PRESS =>
            key match {
              case GLFW_KEY_Q => glfwSetWindowShouldClose(window, true)

              case _ => stateMachine.inputChanged(key)
            }

          case GLFW_RELEASE => stateMachine.inputChanged(GLFW_RELEASE)

          case _ =>  // GLFW_REPEAT
        }

    glfwSetKeyCallback(window, fn)
  }

  def tickInput(): Unit = {
    glfwPollEvents()
  }

}
