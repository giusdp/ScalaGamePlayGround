package datamanager

import game_object_system.graphics_objects.{ModelRect, Sprite, TextureAtlas}

object SpriteLoader {

  def loadSprite(imgFileName : String): Option[Sprite] = loadSprite(0,0,imgFileName)

  def loadSprite(x : Int, y : Int, imgFileName : String): Option[Sprite] =
    TextureLoader.loadTexture(imgFileName) match {
      case Some(t) =>  Some(Sprite(ModelLoader.loadModel(ModelRect(x,y, t.w, t.h)), t))
      case None => None
    }
/*

  def loadSpriteSheet(fileName : String, cellSize : Int): Option[SpriteSheet] =
    TextureLoader.loadTexture(fileName) match {
      case Some(t) =>  Some(SpriteSheet(t, cellSize))
      case None => None
    }
*/

}
