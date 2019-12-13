package game_object_system.graphics_objects

import org.joml.{Matrix4f, Vector3f}

object Camera {

  var aspectRatio : Float = _

  val position : Vector3f = new Vector3f(0,0,10)
  var scaleFactor :Float = 3f
  var proj : Matrix4f = _

  def setViewSize(w : Float, h: Float): Unit = proj = new Matrix4f().setOrtho(-w/2, w/2, -h/2, h/2, -100, 100)

  def setPosition(x : Float, y : Float): Unit = {
    position.x = -x * scaleFactor
    position.y = -y * scaleFactor
  }

  def getProjection: Matrix4f = {
    val view = new Matrix4f().setTranslation(position).scale(scaleFactor)
    proj.mul(view, new Matrix4f())
  }

  def zoomIn(): Unit = scaleFactor += 1f
  def zoomOut(): Unit = if (scaleFactor > 0) scaleFactor -= 1f

}
