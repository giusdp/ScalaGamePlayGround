package simulation


object MovementStateMachine {

  val stateStack : List[MovementState] = List(Standing)

  def currentState: MovementState = stateStack.last

  def update(key : Int): Unit =  currentState.inputChanged(key)

}
