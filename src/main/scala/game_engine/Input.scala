package game_engine

import game_object_system.{ECHandler, InputCom, MovableCom}
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.GLFWKeyCallbackI
import simulation.MovementStateMachine

object Input {

  def registerInput(window : Long): Unit = {
    ECHandler.entitiesWithThisComponent[InputCom] match {
      case e :: _ => ECHandler.getThisComponentOfE[MovableCom](e) match {
        case Some(c) => Input.setupCallbacks(window, c.state_machine)
        case _ =>
      }
      case _ =>
    }
  }

  def setupCallbacks(window: Long, stateMachine: MovementStateMachine): Unit = {
    val fn : GLFWKeyCallbackI =
      (window: Long, key: Int, _: Int, action: Int, _: Int) =>
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
