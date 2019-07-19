package game_object_system.graphics_objects

import org.joml.{Matrix4f, Vector2f}

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
                     var currentAnimation : Animation, var currentFrame : Int = 0) extends Sprite(model, texture){

  var textureFrameCoords: Array[Vector2f] = getFrameCoords
  var fps: Float = 1.0f/currentAnimation.duration.toFloat
  def nextFrame(): Unit = {
    currentFrame += 1
    if (currentFrame >= currentAnimation.numFrames) currentFrame = 0
    updateFrameCoords()
  }

  private def updateFrameCoords(): Unit = textureFrameCoords = getFrameCoords
  private def getFrameCoords = currentAnimation.frames(currentFrame)

}

case class Animation(name: String, numFrames : Int, frames : Array[Array[Vector2f]], duration : Int = 6)