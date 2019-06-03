package game_object_system

import simulation.MovementStateMachine

// Ordered is used to order components based on the number of other components required
trait Component extends Ordered[Component]

case class EmptyCom() extends Component {
  override def compare(that: Component): Int = 0
}

case class PositionCom(var x: Float, var y: Float) extends Component{
  def addToX(nx : Float): Unit = x += nx
  def addToY(ny : Float): Unit = y += ny

  override def compare(that: Component): Int = 0 // needs 0 other components
}

case class MovableCom(var velX: Float, velY: Float) extends Component {
  var state_machine = new MovementStateMachine()
  def movePos(pos: PositionCom): Unit = state_machine.updatePos(pos, (velX, velY))

  override def compare(that: Component): Int = 1
}

case class InputCom() extends Component {
  override def compare(that: Component): Int = 1
}

case class RenderableCom() extends Component {
  override def compare(that: Component): Int = 2
}