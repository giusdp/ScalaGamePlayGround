package game_engine.graphics

import java.nio.FloatBuffer

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import datamanager.ModelLoader
import game_object_system.graphics_objects.{Camera, Model}
import game_object_system.graphics_objects.shaders.TileMapShader
import game_object_system.{ECEngine, TileMapCom}
import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL11, GL31}

import scala.collection.mutable.ArrayBuffer

class TileMapRenderer(shader: TileMapShader, priority : Int) extends IteratingSystem(
  Family.all(classOf[TileMapCom]).get(), priority){

  val fb: FloatBuffer = BufferUtils.createFloatBuffer(16)

  val quad: Model = buildTrans()

  def buildTrans() : Model = {
    val ts : ArrayBuffer[Float] = ArrayBuffer()
    for (y <- -10 until 10 by 2) for (x <- -10 until 10 by 2){
      ts.addOne(x/10.0f)
      ts.addOne(y/10.0f)
      ts.addOne(0)
    }
    ModelLoader.loadTileMapModel(ts.toArray)
  }

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val map = ECEngine.tileMapMapper.get(entity).map

    shader.use()
    map.tileLayers.foreach(l => {
      l.tmModel.bindModel()
      val mvp = Camera.getProjection.mulOrthoAffine(l.tmModel.model_matrix.scale(64, new Matrix4f())).get(fb)
      shader.loadMVP(mvp)
      GL31.glDrawArraysInstanced(GL11.GL_TRIANGLES,0, 6, l.nTiles)
      l.tmModel.unBindModel()
    })
    shader.stop()
  }
}

