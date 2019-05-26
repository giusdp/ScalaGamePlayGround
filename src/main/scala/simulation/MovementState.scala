package simulation

import org.lwjgl.glfw.GLFW.{GLFW_KEY_A, GLFW_KEY_D, GLFW_KEY_S, GLFW_KEY_W, GLFW_RELEASE}

sealed trait MovementState{
  def enter()
  def exit()
  def inputChanged(key : Int)
}
case object Standing extends MovementState {
  override def enter(): Unit = println("Entered standing state")

  override def exit(): Unit = println("Exited standing state")

  override def inputChanged(key : Int): Unit = key match {
    case GLFW_KEY_A | GLFW_KEY_W | GLFW_KEY_S | GLFW_KEY_D => MovementStateMachine.changeState(Walking)
    case _ =>
  }
}
case object Walking extends MovementState {
  override def enter(): Unit = println("Entered walking state")

  override def exit(): Unit = println("Exited walking state")

  override def inputChanged(key : Int): Unit = key match {
    case GLFW_RELEASE => MovementStateMachine.changeState(Standing)
    case _ =>
  }
}