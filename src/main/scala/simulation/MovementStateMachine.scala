package simulation


object MovementStateMachine {

  val stateStack : List[MovementState] = List(Standing)

  def currentState: MovementState = stateStack.last

}
