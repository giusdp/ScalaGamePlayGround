package game_object_system

import simulation.MovementStateMachine

sealed trait Component

case class EmptyCom() extends Component

case class PositionCom(var x: Float, var y: Float) extends Component{
  def addToX(nx : Float): Unit = x += nx
  def addToY(ny : Float): Unit = y += ny
}

case class MovableCom(var vel: Float) extends Component {
  var state_machine = new MovementStateMachine()
  def movePos(pos: PositionCom): Unit = state_machine.updatePos(pos, vel)
}

case class InputCom() extends Component
