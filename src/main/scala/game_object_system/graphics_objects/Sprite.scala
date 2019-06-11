package game_object_system.graphics_objects

case class Sprite(model : Model, texture: Texture) {
  def dispose(): Unit = {
    model.dispose()
    texture.dispose()
  }
}
