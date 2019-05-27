package simulation

import org.lwjgl.glfw.GLFW.{GLFW_KEY_A, GLFW_KEY_D, GLFW_KEY_S, GLFW_KEY_W, GLFW_RELEASE}

sealed trait MovementState{
  def inputChanged(stateMachine: MovementStateMachine, key : Int)
}

case object Standing extends MovementState {
  override def inputChanged(stateMachine: MovementStateMachine, key : Int): Unit = key match {
    case GLFW_KEY_A | GLFW_KEY_W | GLFW_KEY_S | GLFW_KEY_D => stateMachine.pushState(Walking)
    case _ =>
  }

  override def toString: String = "Standing"
}

case object Walking extends MovementState {
  override def inputChanged(stateMachine: MovementStateMachine, key : Int): Unit = key match {
    case GLFW_KEY_A | GLFW_KEY_W | GLFW_KEY_S | GLFW_KEY_D => stateMachine.pushState(Walking)
    case GLFW_RELEASE => stateMachine.popState()
    case _ =>
  }

  override def toString: String = "Walking"

}