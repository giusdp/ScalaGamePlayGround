package datamanager

import game_object_system.graphics_objects.{Quad, Shape, SpriteCom}

object SpriteLoader {

  def loadQuadSprite(imgFileName : String): SpriteCom = loadSprite(Quad(), imgFileName)

  def loadSprite(s : Shape, imgFileName : String): SpriteCom = {
    SpriteCom(ModelLoader.loadModel(s), TextureLoader.loadTexture(imgFileName))
  }



}
