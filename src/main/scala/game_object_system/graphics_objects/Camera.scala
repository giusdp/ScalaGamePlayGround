package game_object_system.graphics_objects

import org.joml.{Matrix4f, Vector3f}

object Camera {

  val position : Vector3f = new Vector3f(0,0,0)
  val scale = new Vector3f(1,1,1)

  var proj : Matrix4f = new Matrix4f()

  def setViewSize(w : Float, h: Float): Unit =
    proj = new Matrix4f().setOrtho2D(-w / 2, w / 2, -h / 2, h / 2)

  def setPosition(x : Float, y : Float, z : Float): Unit = position.set(x, y, z)

  def addPosition(x : Float, y : Float, z : Float): Unit = position.add(x, y, z)

  def viewProjMat: Matrix4f =
    proj.mulOrthoAffine(new Matrix4f().setTranslation(position).scaling(scale), new Matrix4f())

  def zoomIn(): Vector3f = scale.add(0.2f, 0.2f, 0)
  def zoomOut(): Vector3f = scale.add(-0.2f, -0.2f, 0)


}
