package game_object_system.graphics_objects

import game_object_system.Constants
import org.joml.{Matrix4f, Vector3f}
import game_object_system.Constants.{WIDTH, HEIGHT}

object Camera {

  val position : Vector3f = new Vector3f()

  val proj : Matrix4f = new Matrix4f().setOrtho2D(-WIDTH / 2, WIDTH / 2,
    -HEIGHT / 2, HEIGHT / 2)

  def setPosition(x : Float, y : Float, z : Float): Unit = position.set(x, y, z)

  def addPosition(x : Float, y : Float, z : Float): Unit = position.add(x, y, z)

  def viewProjMat: Matrix4f = proj.mulOrthoAffine(new Matrix4f().setTranslation(position), new Matrix4f())
}
