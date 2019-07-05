package game_object_system.graphics_objects

import org.joml.{Matrix4f, Vector3f}

object Camera {

  val position : Vector3f = new Vector3f(0,0,0)
  var scaleFactor = 1f
  var proj : Matrix4f = _

  def setViewSize(w : Float, h: Float): Unit = {
    val aspectRatio = w/h
    val viewSize : Float = 900
    proj = new Matrix4f().setOrtho2D(-aspectRatio*viewSize / 2, aspectRatio*viewSize / 2,
      -viewSize / 2, viewSize / 2)
  }
  def setPosition(x : Float, y : Float, z : Float): Unit = position.set(x, y, z)

  def move(x : Float, y : Float, z : Float): Unit = position.add(x, y, z)

  def projection(): Matrix4f = {
    val pos = new Matrix4f().setTranslation(position).scale(scaleFactor)
    proj.mul(pos, new Matrix4f())
  }

  def zoomIn(): Unit = scaleFactor += 0.1f
  def zoomOut(): Unit = scaleFactor -= 0.1f

}
