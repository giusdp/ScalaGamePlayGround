package simulation

import org.lwjgl.glfw.GLFW.{GLFW_KEY_A, GLFW_KEY_D, GLFW_KEY_S, GLFW_KEY_W, GLFW_RELEASE}

object d extends Enumeration{
  val LEFT: (Float, Float) = (-1,0)
  val UP: (Float, Float) = (0, 1)
  val DOWN: (Float, Float) = (0, -1)
  val RIGHT: (Float, Float) = (1, 0)
}

sealed abstract class MovementState(dir : (Float, Float)){
  def inputChanged(stateMachine: MovementStateMachine, key : Int)
  def getNewDir(newDir : (Float, Float)): (Float, Float) = (dir._1 + newDir._1, dir._2 + newDir._2)
  def getDir: (Float, Float) = dir
}

case class Standing() extends MovementState((0,0)) {
  override def inputChanged(stateMachine: MovementStateMachine, key : Int): Unit = key match {
    case GLFW_KEY_A => stateMachine.pushState(Walking(getNewDir(d.LEFT)))
    case GLFW_KEY_W => stateMachine.pushState(Walking(getNewDir(d.UP)))
    case GLFW_KEY_S => stateMachine.pushState(Walking(getNewDir(d.DOWN)))
    case GLFW_KEY_D => stateMachine.pushState(Walking(getNewDir(d.RIGHT)))
    case _ =>
  }

  override def toString: String = "Standing"
}

case class Walking(dir : (Float, Float)) extends MovementState(dir) {
  override def inputChanged(stateMachine: MovementStateMachine, key : Int): Unit = key match {
    case GLFW_KEY_A => stateMachine.pushState(Walking(getNewDir(d.LEFT)))
    case GLFW_KEY_W => stateMachine.pushState(Walking(getNewDir(d.UP)))
    case GLFW_KEY_S => stateMachine.pushState(Walking(getNewDir(d.DOWN)))
    case GLFW_KEY_D => stateMachine.pushState(Walking(getNewDir(d.RIGHT)))
    case GLFW_RELEASE => stateMachine.popState()
    case _ =>
  }

  override def toString: String = "Walking"

}