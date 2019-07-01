package datamanager

import game_object_system.graphics_objects.TextureAtlas

object TilesLoader {

  def loadTiles(filename: String, tileSize : Float) = {
    TextureLoader.loadTexture(filename) match {
      case Some(t) =>
        val ta : TextureAtlas = TextureAtlas(t, tileSize, t.w, t.h)



      case None => None
    }
  }

}
