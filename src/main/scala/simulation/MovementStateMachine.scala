package simulation

import game_object_system.PositionCom

class MovementStateMachine {

  var stackStates : List[MovementState] = List(Standing)

  def currentState: MovementState = stackStates.head

  def inputChanged(key : Int): Unit = {
    currentState.inputChanged(this, key)
    println(currentState)
  }

  def update(pos : PositionCom, vel: Int): Unit = currentState match {
    case Walking => pos.addToX(vel)
    case _ =>
  }

  def popState(): Unit = stackStates = stackStates.tail

  def pushState(newState : MovementState): Unit = stackStates = newState :: stackStates


}
