package game_engine.graphics
import game_object_system.graphics_objects.{Model, Shader, SpriteCom}
import game_object_system.{ECHandler, Entity, PositionCom}
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.{GL11, GL20, GL30}

class Renderer(shader : Shader) {

  def renderFrame(window : Long): Unit = {
    glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
    glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

    shader.use()
    ECHandler.renderableEntities.foreach(renderEntity)
    shader.stop()

    glfwSwapBuffers(window); // swap the color buffers
  }

  def renderEntity(e: Entity): Unit = {
    //var r = ECHandler.getThisComponentOfE[RenderableCom](e)

    def extractPosition(op : Option[PositionCom]): Unit = op match {
      case Some(_) => extractSprite(ECHandler.getThisComponentOfE[SpriteCom](e))
      case None =>
    }
    def extractSprite(om : Option[SpriteCom]): Unit = om match {
      case Some(m) => render(m)
      case None =>
    }
    def render(sprite : SpriteCom): Unit = {
      GL30.glBindVertexArray(sprite.model.vao)
//      GL20.glEnableVertexAttribArray(0)

      GL11.glDrawElements(GL11.GL_TRIANGLES, sprite.model.vCount, GL11.GL_UNSIGNED_INT, 0)

//      GL20.glDisableVertexAttribArray(0)
      GL30.glBindVertexArray(0)
    }

    extractPosition(ECHandler.getThisComponentOfE[PositionCom](e))
  }

  def dispose(): Unit = shader.dispose()

}
