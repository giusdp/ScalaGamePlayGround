package datamanager

import game_object_system.graphics_objects.{RectModel, Sprite, StaticSprite}

object SpriteLoader {

  def loadSprite(imgFileName : String): Option[Sprite] = loadSprite(0,0,imgFileName)

  def loadSprite(x : Int, y : Int, imgFileName : String): Option[Sprite] =
    TextureLoader.loadTexture(imgFileName) match {
      case Some(t) =>  Some(new StaticSprite(ModelLoader.loadModel(RectModel(x,y, t.w, t.h)), t))
      case None => None
    }

}
