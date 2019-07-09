package game_engine.graphics

import java.nio.FloatBuffer

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import game_object_system.graphics_objects.Camera
import game_object_system.graphics_objects.shaders.TileMapShader
import game_object_system.{ECEngine, TileMapCom}
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL11, GL30}

class TileMapRenderer(shader: TileMapShader, priority : Int) extends IteratingSystem(
  Family.all(classOf[TileMapCom]).get(), priority){

  val fb: FloatBuffer = BufferUtils.createFloatBuffer(16)

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val map = ECEngine.tileMapMapper.get(entity).map

    shader.use()
    map.pointsArrayModel.bindModel()
    map.tileSet.bindTextureAtlas(0)
    val mvp = Camera.getProjection.get(fb)
    shader.loadMVP(mvp)
    GL11.glDrawArrays(GL11.GL_POINTS, 0, map.pointsArrayModel.vCount)
    map.pointsArrayModel.unBindModel()
    shader.stop()
    //
    //    map.tileLayers.foreach(layer  => {
    //      layer.tiles.foreach(tile => {
    //        GL30.glBindVertexArray(tile.vao)
    //
    //        shader.loadMVP(Camera.getProjection.mulOrthoAffine(tile.model_matrix).get(fb))
    //
    //        GL11.glDrawElements(GL11.GL_TRIANGLES, tile.vCount, GL11.GL_UNSIGNED_INT, 0)
    //
    //        GL30.glBindVertexArray(0)
    //      })
    //    })
  }
}

