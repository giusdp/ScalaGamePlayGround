package game_engine.movement

import game_object_system.DirectionCom

object MoveCommands extends Enumeration {
  val LEFT: (Float, Float) = (-1, 0)
  val UP: (Float, Float) = (0, 1)
  val DOWN: (Float, Float) = (0, -1)
  val RIGHT: (Float, Float) = (1, 0)


  def moveLeft(dirCom: DirectionCom): Unit = dirCom.dir = calcDir(dirCom.dir, LEFT, _+_)

  def moveRight(dirCom: DirectionCom): Unit = dirCom.dir = calcDir(dirCom.dir, RIGHT, _+_)

  def moveUp(dirCom: DirectionCom): Unit = dirCom.dir = calcDir(dirCom.dir, UP, _+_)

  def moveDown(dirCom: DirectionCom): Unit = dirCom.dir = calcDir(dirCom.dir, DOWN, _+_)

  private def calcDir(d1 : (Float, Float), d2 : (Float, Float), f : (Float, Float) => Float): (Float, Float) =
    (f(d1._1, d2._1), f(d1._2, d2._2))


}




