package simulation

import game_object_system.PositionCom

class MovementStateMachine {


  var stackStates : List[MovementState] = List(Standing())

  def currentState: MovementState = stackStates.head

  def inputChanged(key : Int): Unit = {
    currentState.inputChanged(this, key)
//    println(currentState)
  }

  def updatePos(pos : PositionCom, vel: Float): Unit = currentState match {
    case state : Walking =>
      pos.addToX(vel * state.dir._1)
      pos.addToY(vel * state.dir._2)
    case _ =>
  }

  def popState(): Unit = stackStates = stackStates.tail

  def pushState(newState : MovementState): Unit = stackStates = newState :: stackStates


}
