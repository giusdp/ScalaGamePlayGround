package game_engine.graphics
import java.nio.FloatBuffer
import java.util.Comparator

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.SortedIteratingSystem
import game_object_system.graphics_objects.Camera
import game_object_system.graphics_objects.shaders.Shader
import game_object_system.{ECEngine, PositionCom, RenderableCom}
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11

class SpriteRenderer(shader : Shader, priority : Int) extends SortedIteratingSystem(
  Family.all(classOf[PositionCom], classOf[RenderableCom]).get(), Comp.comparator, priority) {

  val fb: FloatBuffer = BufferUtils.createFloatBuffer(16)

  override def processEntity(e: Entity, deltaTime: Float): Unit = {

    val sprite = ECEngine.renderableMapper.get(e).sprite

    shader.use()
    sprite.model.bindModel()
    sprite.texture.bind(0)

    val mvp = Camera.getProjection.mulOrthoAffine(sprite.getModelMatrix)
    shader.loadMVP(mvp.get(fb))
    GL11.glDrawElements(GL11.GL_TRIANGLES, sprite.model.vCount, GL11.GL_UNSIGNED_INT, 0)

    sprite.model.unBindModel()
    shader.stop()
  }

  def dispose(): Unit = {
    if (shader != null)shader.dispose()
  }

}

object Comp {
  val comparator : Comparator[Entity] = (e1, e2) => Math.signum(ECEngine.posMapper.get(e1).z - ECEngine.posMapper.get(e2).z).toInt
}
