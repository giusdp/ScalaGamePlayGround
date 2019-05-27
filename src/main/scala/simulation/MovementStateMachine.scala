package simulation

class MovementStateMachine {

  var stackStates : List[MovementState] = List(Standing)

  def currentState: MovementState = stackStates.last

  def update(key : Int): Unit = {
    currentState.inputChanged(this, key)
    //println(currentState)
  }

  def popState(): Unit = stackStates = stackStates.tail

  def pushState(newState : MovementState): Unit = stackStates = newState :: stackStates


}
