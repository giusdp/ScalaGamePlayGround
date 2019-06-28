package game_object_system

import com.badlogic.ashley.core.Component
import game_object_system.graphics_objects.Sprite

case class EmptyCom() extends Component

case class PositionCom(var x: Float, var y: Float, var z : Float) extends Component{
  def setX(nx : Float): Unit = x = nx
  def setY(ny : Float): Unit = y = ny
  def setZ(nz : Float): Unit = z = nz
}

case class VelocityCom(var velX: Float, var velY: Float) extends Component

// Instead of forcing sprites as the objects to use to render, it could be abstracted away so to
// allow anything to be rendered by accessing it through this component in the renderer.
case class RenderableCom(sprite : Sprite) extends Component

case class CameraCenterCom() extends Component