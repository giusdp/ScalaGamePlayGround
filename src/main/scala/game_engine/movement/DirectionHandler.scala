package game_engine.movement

import org.lwjgl.glfw.GLFW.{GLFW_KEY_A, GLFW_KEY_D, GLFW_KEY_S, GLFW_KEY_W}


object d extends Enumeration{
  val LEFT: (Float, Float) = (-1,0)
  val UP: (Float, Float) = (0, 1)
  val DOWN: (Float, Float) = (0, -1)
  val RIGHT: (Float, Float) = (1, 0)
}

class DirectionHandler {

  var direction: (Float, Float) = (0,0)

  def walking(dir : (Float, Float)): (Float, Float) = {
    calcDir(dir, _+_)
  }
  def stoppedWalking(dir : (Float, Float)): (Float, Float) = {
    calcDir(dir, _-_)
  }

  def changeDirByKey(key : Int, f : ((Float, Float)) => (Float, Float)): (Float, Float) = key match {
    case GLFW_KEY_A => f(d.LEFT)
    case GLFW_KEY_W => f(d.UP)
    case GLFW_KEY_S => f(d.DOWN)
    case GLFW_KEY_D => f(d.RIGHT)
    case _ => (0,0)
  }

  def pressedKey(key : Int): Unit = {
    direction = changeDirByKey(key, walking)
   /* if (direction._1 != 0 && direction._2 != 0) {
      val l = Math.sqrt(direction._1 * direction._1 + direction._2 * direction._2).toFloat
      direction = (direction._1 / l, direction._2 / l)
    }*/
  }

  def releasedKey(key : Int): Unit = direction = changeDirByKey(key, stoppedWalking)

  def calcDir(dir : (Float, Float), f : (Float, Float) => Float): (Float, Float) = {
    val x = f(direction._1, dir._1)
    val y = f(direction._2, dir._2)
    (x,y)
  }

}
