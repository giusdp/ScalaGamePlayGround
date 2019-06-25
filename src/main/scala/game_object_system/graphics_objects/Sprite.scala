package game_object_system.graphics_objects

import org.joml.Matrix4f

case class Sprite(model : Model, texture: Texture) {

  def getModelMatrix: Matrix4f = model.model_matrix

  def moveSprite(x : Float, y : Float, z : Float) : Unit = getModelMatrix.identity().translate (x, y, z)

  val width: Float = texture.w
  val height: Float = texture.h

  def dispose(): Unit = {
    model.dispose()
    texture.dispose()
  }
}
