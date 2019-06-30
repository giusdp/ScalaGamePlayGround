package game_engine.movement

import game_object_system.DirectionCom

object MoveCommands {

  var dx : Float = 0
  var dy : Float = 0

  val UP: (Float, Float) = (0, 1)
  val DOWN: (Float, Float) = (0, -1)
  val LEFT: (Float, Float) = (-1, 0)
  val RIGHT: (Float, Float) = (1, 0)

  def pressedUp(dirCom: DirectionCom): Unit = dirCom.dir = processDir(UP, _+_)
  def pressedDown(dirCom: DirectionCom): Unit = dirCom.dir = processDir(DOWN, _+_)
  def pressedLeft(dirCom: DirectionCom): Unit = dirCom.dir = processDir(LEFT, _+_)
  def pressedRight(dirCom: DirectionCom): Unit = dirCom.dir = processDir(RIGHT, _+_)

  def releasedUp(dirCom: DirectionCom): Unit = dirCom.dir = processDir(UP, _-_)
  def releasedDown(dirCom: DirectionCom): Unit = dirCom.dir = processDir(DOWN, _-_)
  def releasedLeft(dirCom: DirectionCom): Unit = dirCom.dir = processDir(LEFT, _-_)
  def releasedRight(dirCom: DirectionCom): Unit = dirCom.dir = processDir(RIGHT, _-_)

  private def processDir(dir : (Float, Float), f : (Float, Float) => Float): (Float, Float) = {
    dx = f(dx, dir._1)
    dy = f(dy, dir._2)
    if (dx != 0 && dy != 0)(dx/l, dy/l)
    else (dx, dy)
  }

  val l: Float = Math.sqrt(2).toFloat

}




