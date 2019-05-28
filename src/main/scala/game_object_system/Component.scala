package game_object_system

import simulation.MovementStateMachine

sealed trait Component

final case class PositionCom(var x: Int, var y: Int) extends Component

final case class MovableCom(var vel: Int) extends Component {
  var state_machine = new MovementStateMachine()
}

//final case class MovableCom(var velocity: Int) extends Component
