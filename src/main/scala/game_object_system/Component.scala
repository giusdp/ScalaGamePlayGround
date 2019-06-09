package game_object_system

import org.joml.Matrix4f
import simulation.MovementHandler

// Ordered is used to order components based on the number of other components required
trait Component extends Ordered[Component]{
  val priority : Int = 0

  override def compare(that: Component): Int = priority compare that.priority

}

case class EmptyCom() extends Component

case class PositionCom(var x: Float, var y: Float) extends Component{
  val model_matrix: Matrix4f = new Matrix4f()
  def addToX(nx : Float): Unit = x += nx
  def addToY(ny : Float): Unit = y += ny

}

case class MovableCom(var velX: Float, var velY: Float) extends Component {
  override val priority = 1
  val state_machine = new MovementHandler()
}

case class InputCom() extends Component {
  override val priority = 2
}

case class RenderableCom() extends Component {
  override val priority = 2
}

case class Camera() extends Component