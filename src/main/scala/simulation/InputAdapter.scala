package simulation

import org.lwjgl.glfw.GLFW.GLFW_KEY_A

object InputAdapter {

  def pressedA(): Unit = MovementStateMachine.update(GLFW_KEY_A)
  def releasedA(): Unit = println("A released")

}
