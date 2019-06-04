package datamanager

import game_object_system.graphics_objects.{Quad, Shape, SpriteCom}

object SpriteLoader {

  def loadQuadSprite(imgFileName : String): Option[SpriteCom] = loadSprite(Quad(), imgFileName)

  def loadSprite(s : Shape, imgFileName : String): Option[SpriteCom] =
    TextureLoader.loadTexture(imgFileName) match {
      case Some(t) =>  Some(SpriteCom(ModelLoader.loadModel(s), t))
      case None => None
    }
}
