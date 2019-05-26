package simulation

import org.lwjgl.glfw.GLFW.{GLFW_KEY_A, GLFW_KEY_W, GLFW_KEY_S, GLFW_KEY_D}

sealed trait MovementState{
  def enter()
  def exit()
  def inputChanged(key : Int)
}
case object Standing extends MovementState {
  override def enter(): Unit = ???

  override def exit(): Unit = ???

  override def inputChanged(key : Int): Unit = key match {
    case GLFW_KEY_A | GLFW_KEY_W | GLFW_KEY_S | GLFW_KEY_D => println("Standing state: movement input received.")
    case _ =>
  }
}
case object Walking extends MovementState {
  override def enter(): Unit = ???

  override def exit(): Unit = ???

  override def inputChanged(key : Int): Unit = ???
}