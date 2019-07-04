package game_engine.graphics

import game_object_system.graphics_objects.{Camera, Shader, TextureAtlas, TileMap}
import org.lwjgl.opengl.{GL11, GL13, GL30}

class TMXMapRenderer(shader: Shader) {

  def renderMap(map : TileMap) = {
    map.layers.foreach(layer => layer.tiles.zipWithIndex.foreach
      {
        case (tile, i) =>
          if (tile.gid == 0) {}
          else {
            val colums = map.tileSet.tsImage.imageWidth / map.tileWidth
            val x = i % colums
            val y = Math.floor(i / colums).toFloat
            println("Rendering tile: " + x + ", " + y)
            GL11.glBegin(GL11.GL_QUADS)

            GL11.glEnd()
          }
      })
  }

  def renderTile(textureAtlas : TextureAtlas) = {
    shader.use()

    GL13.glActiveTexture(0)

    GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureAtlas.texture.id)

    //shader.setMVP(Camera.viewProjMat.mulAffine(sprite.getModelMatrix).get(fb))

//    GL11.glDrawElements(GL11.GL_TRIANGLES, sprite.model.vCount, GL11.GL_UNSIGNED_INT, 0)

    GL30.glBindVertexArray(0)
    shader.stop()
  }
}

