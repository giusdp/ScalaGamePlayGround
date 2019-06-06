package simulation

import game_object_system.PositionCom

class MovementStateMachine {


  var stackStates : List[MovementState] = List(Standing())

  def currentState: MovementState = stackStates.head

  def inputChanged(key : Int): Unit = {
    currentState.inputChanged(this, key)
    println(currentState)
  }

  def updatePos(pos : PositionCom, vel: (Float, Float)): Unit = currentState match {
    case state : Walking =>
      pos.addToX(vel._1 * state.dir._1)
      pos.addToY(vel._2 * state.dir._2)
    case _ =>
  }
  def currentDirection: (Float, Float) = currentState.getDir

  def popState(): Unit = stackStates = stackStates.tail

  def pushState(newState : MovementState): Unit = stackStates = newState :: stackStates

  def isStill: Boolean = currentState.isInstanceOf[Standing]
}
