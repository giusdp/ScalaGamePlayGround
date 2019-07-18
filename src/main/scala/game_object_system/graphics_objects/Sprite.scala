package game_object_system.graphics_objects

import org.joml.Matrix4f

abstract case class Sprite(model : Model, texture: Texture) {
  def getModelMatrix: Matrix4f = model.model_matrix

  def moveSprite(x : Float, y : Float, z : Float) : Unit = model.move(x, y, z)

  def getWidth: Float = texture.getWidth
  def getHeight: Float = texture.getHeight

  def dispose(): Unit = {
    model.dispose()
    texture.dispose()
  }
}

class StaticSprite(model : Model, texture: StaticTexture) extends Sprite(model, texture)

class AnimatedSprite(model : Model, texture: TextureAtlas, animations : List[Animation],
                     currentAnimation : String = "idle", currentFrame : Int = 0, fps : Int = 16) extends Sprite(model, texture){

}

case class Animation(name: String, numFrames : Int, frames : List[Array[Float]])