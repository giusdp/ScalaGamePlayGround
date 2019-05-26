package simulation

import org.lwjgl.glfw.GLFW.{GLFW_KEY_A, GLFW_RELEASE}

object InputAdapter {

  def pressedA(): Unit = MovementStateMachine.update(GLFW_KEY_A)
  def releasedA(): Unit = MovementStateMachine.update(GLFW_RELEASE)

}
