package simulation

sealed trait MovementState
case class Standing() extends MovementState
case class Walking() extends MovementState
case class Running() extends MovementState


class MovementStateMachine {



}
