package datamanager

import game_object_system.graphics_objects.{Rect, SpriteCom}

object SpriteLoader {

  def loadSprite(x: Float, y : Float, imgFileName : String): Option[SpriteCom] =
    TextureLoader.loadTexture(imgFileName) match {
      case Some(t) =>  Some(SpriteCom(ModelLoader.loadModel(Rect(x, y, t.w, t.h)), t))
      case None => None
  }

}
