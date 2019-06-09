package game_object_system.graphics_objects

import game_object_system.Component

case class SpriteCom(model : Model, texture: Texture) extends Component {
  override val priority: Int = 1
  def dispose(): Unit = {
    model.dispose()
    texture.dispose()
  }
}
