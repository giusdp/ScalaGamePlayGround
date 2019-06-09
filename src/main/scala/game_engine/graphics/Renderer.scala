package game_engine.graphics
import game_object_system.graphics_objects.{Shader, SpriteCom}
import game_object_system.{ECHandler, Entity, PositionCom}
import org.joml.Matrix4f
import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.{GL11, GL13, GL20, GL30}
import java.nio.FloatBuffer

import game_engine.ScreenConstants

class Renderer(shader : Shader) {
  val MVP_LOCATION = 5


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

    def render(p: PositionCom, sprite : SpriteCom): Unit = {
      GL30.glBindVertexArray(sprite.model.vao)
      GL13.glActiveTexture(0)

      GL11.glBindTexture(GL11.GL_TEXTURE_2D, sprite.texture.id)
      val m = new Matrix4f
      m.perspective(Math.toRadians(45).asInstanceOf[Float], ScreenConstants.WIDTH/ScreenConstants.HEIGHT, 0.01f, 1000.0f)
      .lookAt(0.0f, 0.0f, 512.0f,
        0.0f, 0.0f, 0.0f,
        0.0f, 1.0f, 0.0f)
      shader.setMVP(m.mulAffine(p.model_matrix).get(fb))

      GL11.glDrawElements(GL11.GL_TRIANGLES, sprite.model.vCount, GL11.GL_UNSIGNED_INT, 0)

      GL30.glBindVertexArray(0)
    }

    ECHandler.getPositionCom(e) match {
      case Some(p) => ECHandler.getSpriteCom(e) match {
        case Some(s) => render(p, s)
        case None =>
      }
      case None =>
    }
  }

  def dispose(): Unit = shader.dispose()

}
