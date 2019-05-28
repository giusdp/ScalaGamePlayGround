package game_object_system

import simulation.MovementStateMachine

sealed trait Component

final case class PositionCom(var x: Int, var y: Int) extends Component{
  def addToX(nx : Int): Unit = x += nx
  def addToY(ny : Int): Unit = y += ny
}

final case class MovableCom(var vel: Int) extends Component {
  var state_machine = new MovementStateMachine()
  def movePos(pos: PositionCom): Unit = state_machine.updatePos(pos, vel)
}

//final case class MovableCom(var velocity: Int) extends Component
