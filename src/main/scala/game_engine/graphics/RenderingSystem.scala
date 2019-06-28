package game_engine.graphics
import java.nio.FloatBuffer
import java.util.Comparator

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.SortedIteratingSystem
import game_object_system.{ECEngine, PositionCom, VelocityCom}
import game_object_system.graphics_objects.Shader
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL11, GL13, GL30}

class RenderingSystem(shader : Shader, priority : Int) extends SortedIteratingSystem(
  Family.all(PositionCom.getClass, VelocityCom.getClass).get(), Comp.comparator, priority) {

  val fb: FloatBuffer = BufferUtils.createFloatBuffer(16)

  override def processEntity(e: Entity, deltaTime: Float): Unit = {
    shader.use()
    val sprite = ECEngine.renderableMapper.get(e).sprite
    GL30.glBindVertexArray(sprite.model.vao)
    GL13.glActiveTexture(0)

    GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.texture.id)

    shader.setMVP(sprite.getModelMatrix.get(fb))

    GL11.glDrawElements(GL11.GL_TRIANGLES, sprite.model.vCount, GL11.GL_UNSIGNED_INT, 0)

    GL30.glBindVertexArray(0)
    shader.stop()
  }

  def dispose(): Unit = {
    shader.dispose()
  }

//  implicit def sf2jf[T,R](f:(T) => R):java.util.function.Function[T, R] = (t: T) => f(t)
}

object Comp {
  val comparator : Comparator[Entity] = (e1, e2) => Math.signum(ECEngine.posMapper.get(e1).z - ECEngine.posMapper.get(e2).z).toInt
}
