package datamanager

import game_object_system.graphics_objects.{AnimatedSprite, Animation, RectModel, Sprite, StaticSprite, TextureAtlas}

object SpriteLoader {

  def loadStaticSprite(imgFileName : String): Option[Sprite] = loadSprite(0,0,imgFileName)

  def loadSprite(x : Int, y : Int, imgFileName : String): Option[Sprite] =
    TextureLoader.loadTexture(imgFileName).fold(None : Option[Sprite])(t => Some(new StaticSprite(ModelLoader.loadModel(RectModel(x, y, t.w, t.h)), t)))

  def loadAnimatedSprite(imgFileName : String, width : Int, height: Int, animations : Map[String, Map[String, Int]]): Option[Sprite] = {
    try {
      val t: TextureAtlas = TextureLoader.loadTextureAtlas(imgFileName, width, height)
      var anims: Map[String, Animation] = Map()
      animations.foreach {
        case (name, value) =>
          val index = value("startingAt").toString.toInt
          val numFrames = value("frames").toString.toInt
          val frames = (1 to numFrames).map(i => t.extractRegion(index + i)).toArray
          anims = anims + (name -> Animation(numFrames, frames))
      }

      val sprite: AnimatedSprite = new AnimatedSprite(ModelLoader.loadUntexturedModel(RectModel(0, 0, width, height)), t, anims, anims("idle"))
      Some(sprite)
    }
    catch {
      case e : Exception => Console.err.println("LoadAnimatedSprite: " + e.getMessage) ; None
    }
  }

}
