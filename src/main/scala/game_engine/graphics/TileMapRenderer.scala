package game_engine.graphics

import java.nio.FloatBuffer

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import game_object_system.{ECEngine, PositionCom, TileMapCom}
import game_object_system.graphics_objects.{Camera, CustomModel, Shader, Sprite, TextureAtlas, TileLayer, TileMap}
import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL11, GL13, GL30}

class TileMapRenderer(shader: Shader, priority : Int) extends IteratingSystem(
  Family.all(classOf[TileMapCom]).get(), priority){

  val fb: FloatBuffer = BufferUtils.createFloatBuffer(16)

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val map = ECEngine.tileMapMapper.get(entity).map

    shader.use()
    map.tileSet.bindTextureAtlas(0)

    map.tileLayers.foreach(layer  => {
      layer.tiles.foreach(tile => {
        GL30.glBindVertexArray(tile.vao)

        shader.setMVP(Camera.getProjection.mulOrthoAffine(tile.model_matrix).get(fb))

        GL11.glDrawElements(GL11.GL_TRIANGLES, tile.vCount, GL11.GL_UNSIGNED_INT, 0)

        GL30.glBindVertexArray(0)
      })
    })
    shader.stop()
  }
}

