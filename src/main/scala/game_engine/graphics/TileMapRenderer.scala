package game_engine.graphics

import java.nio.FloatBuffer

import game_object_system.graphics_objects.{Camera, CustomModel, Shader, Sprite, TextureAtlas, TileLayer, TileMap}
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL11, GL13, GL30}

class TIleMapRenderer(shader: Shader) {

  def renderMap(map : TileMap): Unit = {
    shader.use()
    GL13.glActiveTexture(0)

    GL11.glBindTexture(GL11.GL_TEXTURE_2D, map.tileSet.texture.id)

    map.mapLayers.foreach(layer  => {
      GL30.glBindVertexArray(layer.vao)

      shader.setMVP(Camera.viewProjMat.mulAffine(layer.model_matrix).get(fb))

      GL11.glDrawElements(GL11.GL_TRIANGLES, layer.vCount, GL11.GL_UNSIGNED_INT, 0)

      GL30.glBindVertexArray(0)
    })
    shader.stop()
  }
    //    map.tileLayers.foreach(layer => layer.tiles.zipWithIndex.foreach
//      {
//        case (tile, i) =>
//          if (tile.gid == 0) {}
//          else {
//            val colums = map.tileSet.tsImage.imageWidth / map.tileWidth
//            val x = i % colums
//            val y = Math.floor(i / colums).toFloat
//            println("Rendering tile: " + x + ", " + y)
//            GL11.glBegin(GL11.GL_QUADS)
//
//            GL11.glEnd()
//          }
//      })

  def renderLayer(layer : TileLayer): Unit = {
    for (x <- 0 until layer.width) for (y <- 0 until layer.height) {

    }
  }

  val fb: FloatBuffer = BufferUtils.createFloatBuffer(16)

}

