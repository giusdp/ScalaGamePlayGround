package simulation

sealed trait MovementState
case object Standing extends MovementState
case object Walking extends MovementState
case object Running extends MovementState


object MovementStateMachine {

  val stateStack : List[MovementState] = List(Standing)

  def currentState = stateStack.last

  def enterState() = ???

  def exitState() = ???

}
