package game_object_system

import com.badlogic.ashley.core.Component
import game_object_system.graphics_objects.Sprite

case class EmptyCom() extends Component

case class PositionCom(var x: Float, var y: Float) extends Component{
  def addToX(nx : Float): Unit = x += nx
  def addToY(ny : Float): Unit = y += ny
}

case class VelocityCom(var velX: Float, var velY: Float) extends Component

// Instead of forcing sprites as the objects to use to render, it could be abstracted away so to
// allow anything to be rendered by accessing it through this component in the renderer.
case class RenderableCom(sprite : Sprite) extends Component

case class CameraCenter() extends Component