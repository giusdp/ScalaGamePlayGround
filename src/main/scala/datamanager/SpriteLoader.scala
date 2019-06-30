package datamanager

import game_object_system.graphics_objects.{Rect, Sprite}

object SpriteLoader {

  def loadSprite(imgFileName : String): Option[Sprite] =
    TextureLoader.loadTexture(imgFileName) match {
      case Some(t) =>  Some(Sprite(ModelLoader.loadModel(Rect(0,0, t.w, t.h)), t))
      case None => None
    }

  def loadSprite(x : Int, y : Int, imgFileName : String): Option[Sprite] =
    TextureLoader.loadTexture(imgFileName) match {
      case Some(t) =>  Some(Sprite(ModelLoader.loadModel(Rect(x,y, t.w, t.h)), t))
      case None => None
    }

}
