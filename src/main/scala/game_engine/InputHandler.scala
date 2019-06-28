package game_engine

import com.badlogic.ashley.core.Entity
import game_object_system.ECEngine
import game_object_system.graphics_objects.Camera
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.GLFWKeyCallbackI

object InputHandler {

  def registerInput(window : Long, player : Entity): Unit = {
    val fn : GLFWKeyCallbackI =
      (window: Long, key: Int, _: Int, action: Int, _: Int) =>
        action match {

          case GLFW_PRESS =>
            key match {
              case GLFW_KEY_Q => glfwSetWindowShouldClose(window, true)

              case GLFW_KEY_W | GLFW_KEY_S | GLFW_KEY_A | GLFW_KEY_D =>
                ECEngine.msmMpper.get(player).msm.pressedKey(key)


              case GLFW_KEY_Z => Camera.zoomIn()
              case GLFW_KEY_X => Camera.zoomOut()

              case _ =>
            }

          case GLFW_RELEASE => key match {
            case GLFW_KEY_W | GLFW_KEY_S | GLFW_KEY_A | GLFW_KEY_D =>
              ECEngine.msmMpper.get(player).msm.releasedKey(key)
            case _ =>
          }

          case _ =>  // GLFW_REPEAT
        }

    glfwSetKeyCallback(window, fn)
  }


}
