package simulation

import org.lwjgl.glfw.GLFW.{GLFW_KEY_A, GLFW_KEY_D, GLFW_KEY_S, GLFW_KEY_W}


object d extends Enumeration{
  val LEFT: (Float, Float) = (-1,0)
  val UP: (Float, Float) = (0, 1)
  val DOWN: (Float, Float) = (0, -1)
  val RIGHT: (Float, Float) = (1, 0)
}

class MovementHandler {

  var direction: (Float, Float) = (0,0)

  def walking(dir : (Float, Float)): Unit = {
    direction = calcDir(dir, _+_)
  }
  def stoppedWalking(dir : (Float, Float)): Unit = {
    direction = calcDir(dir, _-_)
  }

  def funOverKey(key : Int, f : ((Float, Float)) => Unit): Unit = key match {
    case GLFW_KEY_A => f(d.LEFT)
    case GLFW_KEY_W => f(d.UP)
    case GLFW_KEY_S => f(d.DOWN)
    case GLFW_KEY_D => f(d.RIGHT)
    case _ =>
  }

  def pressedKey(key : Int): Unit = funOverKey(key, walking)

  def releasedKey(key : Int): Unit = funOverKey(key, stoppedWalking)

  def calcDir(dir : (Float, Float), f : (Float, Float) => Float): (Float, Float) = {
    val x = f(direction._1, dir._1)
    val y = f(direction._2, dir._2)
    (x,y)
  }

}
