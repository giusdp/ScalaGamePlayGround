package game_engine

import game_object_system.{ECHandler, Entity, MovableCom}
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.GLFWKeyCallbackI
import simulation.MovementHandler

object Input {

  def registerInput(window : Long, e : Entity): Unit = {
    ECHandler.getThisComponentOfE[MovableCom](e) match {
        case Some(c) => Input.setupCallbacks(window, c.state_machine)
        case _ =>
      }
  }

  def setupCallbacks(window: Long, stateMachine: MovementHandler): Unit = {
    val fn : GLFWKeyCallbackI =
      (window: Long, key: Int, _: Int, action: Int, _: Int) =>
        action match {

          case GLFW_PRESS =>
            key match {
              case GLFW_KEY_Q => glfwSetWindowShouldClose(window, true)

              case _ => stateMachine.pressedKey(key)
            }

          case GLFW_RELEASE => stateMachine.releasedKey(key)
          case _ =>  // GLFW_REPEAT
        }

    glfwSetKeyCallback(window, fn)
  }

  def tickInput(): Unit = {
    glfwPollEvents()
  }

}
