package game_engine.graphics
import java.nio.FloatBuffer

import game_object_system.graphics_objects.{Camera, Shader}
import game_object_system.{ECHandler, Entity, PositionCom, RenderableCom}
import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.{GL11, GL13, GL30}

class Renderer(shader : Shader) {

  val fb: FloatBuffer = BufferUtils.createFloatBuffer(16)

  def renderFrame(window : Long): Unit = {
    glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
    glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

    shader.use()
    ECHandler.renderableEntities.foreach(renderEntity)
    shader.stop()

    glfwSwapBuffers(window); // swap the color buffers
  }

  def renderEntity(e: Entity): Unit = {

    def render(p: PositionCom, r: RenderableCom): Unit = {
      val sprite = r.sprite
      GL30.glBindVertexArray(sprite.model.vao)
      GL13.glActiveTexture(0)

      GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.texture.id)

      val m = Camera.viewProjMat.mul(p.model_matrix)
      shader.setMVP(m.get(fb))

      GL11.glDrawElements(GL11.GL_TRIANGLES, sprite.model.vCount, GL11.GL_UNSIGNED_INT, 0)

      GL30.glBindVertexArray(0)
    }

    ECHandler.getPositionCom(e) match {
      case Some(p) => ECHandler.getRenderableCom(e) match {
        case Some(r) => render(p, r)
        case None =>
      }
      case None =>
    }
  }

  def dispose(): Unit = shader.dispose()

}
