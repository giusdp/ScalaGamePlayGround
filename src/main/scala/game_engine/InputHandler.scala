package game_engine

import com.badlogic.ashley.core.Entity
import game_object_system.graphics_objects.Camera
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.GLFWKeyCallbackI
import simulation.MovementHandler

object Input {

  def registerInput(window : Long, e : Entity): Unit = {
    setupCallbacks(window)
  }

  def setupCallbacks(window: Long): Unit = {
    val fn : GLFWKeyCallbackI =
      (window: Long, key: Int, _: Int, action: Int, _: Int) =>
        action match {

          case GLFW_PRESS =>
            key match {
              case GLFW_KEY_Q => glfwSetWindowShouldClose(window, true)
              case GLFW_KEY_Z => Camera.zoomIn()
              case GLFW_KEY_X => Camera.zoomOut()

              case _ =>
            }

          case GLFW_RELEASE =>
          case _ =>  // GLFW_REPEAT
        }

    glfwSetKeyCallback(window, fn)
  }

  def tickInput(): Unit = {
    glfwPollEvents()
  }

}
