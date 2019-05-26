package simulation

object MovementStateMachine {

  var stackStates : List[MovementState] = List(Standing)

  def currentState: MovementState = stackStates.last

  def update(key : Int): Unit = currentState.inputChanged(key)

  def popState(): Unit = stackStates = stackStates.init

  def pushState(newState : MovementState): Unit = stackStates = stackStates ++ List(newState)


}
