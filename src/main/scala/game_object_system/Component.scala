package game_object_system

import simulation.MovementStateMachine

sealed trait Component

case class EmptyCom() extends Component

case class PositionCom(var x: Double, var y: Double) extends Component{
  def addToX(nx : Double): Unit = x += nx
  def addToY(ny : Double): Unit = y += ny
}

case class MovableCom(var velX: Double, velY: Double) extends Component {
  var state_machine = new MovementStateMachine()
  def movePos(pos: PositionCom): Unit = state_machine.updatePos(pos, (velX, velY))
}

case class InputCom() extends Component

case class RenderableCom() extends Component