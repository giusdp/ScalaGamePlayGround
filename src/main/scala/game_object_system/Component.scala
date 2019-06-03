package game_object_system

import simulation.MovementStateMachine

// Ordered is used to order components based on the number of other components required
trait Component extends Ordered[Component]{
  val priority : Int = 0

  override def compare(that: Component): Int = priority compare that.priority

}

case class EmptyCom() extends Component

case class PositionCom(var x: Float, var y: Float) extends Component{
  def addToX(nx : Float): Unit = x += nx
  def addToY(ny : Float): Unit = y += ny

}

case class MovableCom(var velX: Float, velY: Float) extends Component {
  override val priority = 1
  var state_machine = new MovementStateMachine()
  def movePos(pos: PositionCom): Unit = state_machine.updatePos(pos, (velX, velY))

}

case class InputCom() extends Component {
  override val priority = 2
}

case class RenderableCom() extends Component {
  override val priority = 2
}