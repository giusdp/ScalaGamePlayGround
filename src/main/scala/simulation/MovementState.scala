package simulation

import org.lwjgl.glfw.GLFW.{GLFW_KEY_A, GLFW_KEY_D, GLFW_KEY_S, GLFW_KEY_W, GLFW_RELEASE}

sealed trait MovementState{
  def inputChanged(key : Int)
}

case object Standing extends MovementState {
  override def inputChanged(key : Int): Unit = key match {
    case GLFW_KEY_A | GLFW_KEY_W | GLFW_KEY_S | GLFW_KEY_D => MovementStateMachine.pushState(Walking)
    case _ =>
  }

  override def toString: String = "Standing"
}

case object Walking extends MovementState {
  override def inputChanged(key : Int): Unit = key match {
    case GLFW_KEY_A | GLFW_KEY_W | GLFW_KEY_S | GLFW_KEY_D => MovementStateMachine.pushState(Walking)
    case GLFW_RELEASE => MovementStateMachine.popState()
    case _ =>
  }

  override def toString: String = "Walking"

}