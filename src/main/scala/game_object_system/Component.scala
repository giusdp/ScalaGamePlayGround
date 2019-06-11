package game_object_system

import game_object_system.graphics_objects.Sprite
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

// Instead of forcing sprites as the objects to use to render, it could be abstracted away so to
// allow anything to be rendered by accessing it through this component in the renderer.
case class RenderableCom(sprite : Sprite) extends Component {
  override val priority = 2
}

case class CameraCenter() extends Component