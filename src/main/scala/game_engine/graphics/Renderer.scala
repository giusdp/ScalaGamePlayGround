package game_engine.graphics
import game_object_system.{ECHandler, Entity, ModelCom, PositionCom, Shader}
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.{GL11, GL20, GL30}

class Renderer(shader : Shader) {

  def useShader(): Unit = GL20.glUseProgram(shader.getProgram)

  def stopShader(): Unit = GL20.glUseProgram(0)

  def renderFrame(window : Long): Unit = {

    glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

    // set the color of the quad (R,G,B,A)// set the color of the quad (R,G,B,A)

    ECHandler.renderableEntities.foreach(renderEntity)

    glfwSwapBuffers(window); // swap the color buffers
  }

  def renderEntity(e: Entity): Unit = {
    //var r = ECHandler.getThisComponentOfE[RenderableCom](e)

    def extractPosition(op : Option[PositionCom]): Unit = op match {
      case Some(pos) => extractModel(ECHandler.getThisComponentOfE[ModelCom](e))
      case None =>
    }
    def extractModel(om : Option[ModelCom]): Unit = om match {
      case Some(m) => render(m)
      case None =>
    }
    def render(model : ModelCom): Unit = {
      useShader()
      // Bind to the VAO that has all the information about the quad vertices
      GL30.glBindVertexArray(model.vao)
      GL20.glEnableVertexAttribArray(0)

      // Draw the vertices
      GL11.glDrawElements(GL11.GL_TRIANGLES, model.vCount, GL11.GL_UNSIGNED_INT, 0)

      // Put everything back to default (deselect)
      GL20.glDisableVertexAttribArray(0)
      GL30.glBindVertexArray(0)
      stopShader()
    }

    extractPosition(ECHandler.getThisComponentOfE[PositionCom](e))
  }

  def dispose(): Unit = shader.dispose()

}
