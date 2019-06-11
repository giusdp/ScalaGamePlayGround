package game_object_system.graphics_objects

import org.joml.Matrix4f

object Camera {

  var z : Float = 512
  var view_matrix = new Matrix4f()

  def updateCamera(x : Float, y : Float): Unit = {
    view_matrix = new Matrix4f().lookAt(
      x, y, z,
      x, y, 0.0f,
      0.0f, 1.0f, 0.0f)
  }

}
