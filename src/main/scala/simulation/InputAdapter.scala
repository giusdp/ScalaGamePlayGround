package simulation

import org.lwjgl.glfw.GLFW.{GLFW_KEY_A, GLFW_KEY_D, GLFW_KEY_W, GLFW_KEY_S, GLFW_KEY_LEFT_SHIFT, GLFW_RELEASE}

object InputAdapter {

  def pressedA(): Unit = MovementStateMachine.update(GLFW_KEY_A)
  def pressedD(): Unit = MovementStateMachine.update(GLFW_KEY_D)
  def pressedW(): Unit = MovementStateMachine.update(GLFW_KEY_W)
  def pressedS(): Unit = MovementStateMachine.update(GLFW_KEY_S)

  def pressedShift(): Unit = MovementStateMachine.update(GLFW_KEY_LEFT_SHIFT)

  def releasedKey(): Unit = MovementStateMachine.update(GLFW_RELEASE)

}
