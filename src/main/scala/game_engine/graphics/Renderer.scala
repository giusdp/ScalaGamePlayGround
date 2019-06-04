package game_engine.graphics
import game_object_system.{ECHandler, Entity, ModelCom, PositionCom, Shader}
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
      case Some(_) => extractModel(ECHandler.getThisComponentOfE[ModelCom](e))
      case None =>
    }
    def extractModel(om : Option[ModelCom]): Unit = om match {
      case Some(m) => render(m)
      case None =>
    }
    def render(model : ModelCom): Unit = {
      GL30.glBindVertexArray(model.getVAO)
//      GL20.glEnableVertexAttribArray(0)

      GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVCount, GL11.GL_UNSIGNED_INT, 0)

//      GL20.glDisableVertexAttribArray(0)
      GL30.glBindVertexArray(0)
    }

    extractPosition(ECHandler.getThisComponentOfE[PositionCom](e))
  }

  def dispose(): Unit = shader.dispose()

}
