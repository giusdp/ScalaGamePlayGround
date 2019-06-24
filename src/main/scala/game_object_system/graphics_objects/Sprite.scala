package game_object_system.graphics_objects

case class Sprite(model : Model, texture: Texture) {

  val width: Float = texture.w
  val height: Float = texture.h

  def dispose(): Unit = {
    model.dispose()
    texture.dispose()
  }
}
