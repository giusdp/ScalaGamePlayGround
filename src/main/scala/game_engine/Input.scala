package game_engine

import com.badlogic.ashley.core.Entity
import game_engine.graphics.Window
import game_object_system.ECEngine
import game_object_system.graphics_objects.Camera
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw.GLFWKeyCallbackI
import game_engine.movement.MoveCommands._

object Input {

  def registerInput(player : Entity): Unit = {
    val direction = ECEngine.dirMapper.get(player)
    val fn : GLFWKeyCallbackI =
      (window: Long, key: Int, _: Int, action: Int, _: Int) =>
        action match {

          case GLFW_PRESS =>
            key match {
              case GLFW_KEY_Q => glfwSetWindowShouldClose(window, true)

              case GLFW_KEY_W => moveUp(direction)
              case GLFW_KEY_S => moveDown(direction)
              case GLFW_KEY_A => moveLeft(direction)
              case GLFW_KEY_D => moveRight(direction)


              case GLFW_KEY_Z => Camera.zoomIn()
              case GLFW_KEY_X => Camera.zoomOut()

              case _ =>
            }

          case GLFW_RELEASE => key match {
            case GLFW_KEY_W => moveDown(direction)
            case GLFW_KEY_S => moveUp(direction)
            case GLFW_KEY_A => moveRight(direction)
            case GLFW_KEY_D => moveLeft(direction)
            case _ =>
          }

          case _ =>  // GLFW_REPEAT
        }

    glfwSetKeyCallback(Window.window, fn)
  }


}
