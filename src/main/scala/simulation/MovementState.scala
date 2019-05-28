package simulation

import org.lwjgl.glfw.GLFW.{GLFW_KEY_A, GLFW_KEY_D, GLFW_KEY_S, GLFW_KEY_W, GLFW_RELEASE}

object d extends Enumeration{
  val LEFT: (Int, Int) = (-1,0)
  val UP: (Int, Int) = (0, 1)
  val DOWN: (Int, Int) = (0, -1)
  val RIGHT: (Int, Int) = (1, 0)
}

sealed abstract class MovementState(dir : (Int, Int)){
  def inputChanged(stateMachine: MovementStateMachine, key : Int)
  def getNewDir(newDir : (Int, Int)): (Int, Int) = (dir._1 + newDir._1, dir._2 + newDir._2)
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

case class Walking(dir : (Int, Int)) extends MovementState(dir) {
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