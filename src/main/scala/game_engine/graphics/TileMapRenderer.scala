package game_engine.graphics

import java.nio.FloatBuffer

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import game_object_system.{ECEngine, PositionCom, TileMapCom}
import game_object_system.graphics_objects.{Camera, CustomModel, Shader, Sprite, TextureAtlas, TileLayer, TileMap}
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL11, GL13, GL30}

class TileMapRenderer(shader: Shader, priority : Int) extends IteratingSystem(
  Family.all(classOf[TileMapCom]).get(), priority){

  def renderMap(map : TileMap): Unit = {
    shader.use()
    GL13.glActiveTexture(0)

    GL11.glBindTexture(GL11.GL_TEXTURE_2D, map.tileSet.texture.id)

    map.tileLayers.foreach(layer  => {
      layer.tiles.foreach(tile => {
        GL30.glBindVertexArray(tile.vao)

        shader.setMVP(Camera.viewProjMat.mulAffine(tile.model_matrix).get(fb))

        GL11.glDrawElements(GL11.GL_TRIANGLES, tile.vCount, GL11.GL_UNSIGNED_INT, 0)

        GL30.glBindVertexArray(0)
      })
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

  val fb: FloatBuffer = BufferUtils.createFloatBuffer(16)

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val map = ECEngine.tileMapMapper.get(entity).map
    shader.use()
    GL13.glActiveTexture(0)

    GL11.glBindTexture(GL11.GL_TEXTURE_2D, map.tileSet.texture.id)

    map.tileLayers.foreach(layer  => {
      layer.tiles.foreach(tile => {
        GL30.glBindVertexArray(tile.vao)

        shader.setMVP(Camera.viewProjMat.mulAffine(tile.model_matrix).get(fb))

        GL11.glDrawElements(GL11.GL_TRIANGLES, tile.vCount, GL11.GL_UNSIGNED_INT, 0)

        GL30.glBindVertexArray(0)
      })
    })
    shader.stop()
  }
}

