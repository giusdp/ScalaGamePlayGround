package game_engine.graphics

import java.nio.FloatBuffer

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import game_object_system.graphics_objects.Camera
import game_object_system.graphics_objects.shaders.TileMapShader
import game_object_system.{ECEngine, TileMapCom}
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL11, GL31}

class TileMapRenderer(shader: TileMapShader, priority : Int) extends IteratingSystem(
  Family.all(classOf[TileMapCom]).get(), priority){

  val fb: FloatBuffer = BufferUtils.createFloatBuffer(16)

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val map = ECEngine.tileMapMapper.get(entity).map
    shader.use()
    map.tileSet.bindTextureAtlas(1)

    map.tileLayers.foreach(l => {
      l.tmModel.bindModel()
      val mvp = Camera.getProjection.mulOrthoAffine(l.tmModel.model_matrix).get(fb)
      shader.loadMVP(mvp)
      GL31.glDrawArraysInstanced(GL11.GL_TRIANGLES, 0, 6, 1) //l.nTiles)
      l.tmModel.unBindModel()
    })
    shader.stop()
  }
}

