package game_engine.graphics
import game_object_system.{ECHandler, Entity, ModelCom, PositionCom, Shader}
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11._
import org.lwjgl.opengl.{GL11, GL20}

class Renderer {

  var shader : Shader = _
  def setShader(s : Shader) = shader = s

  def renderFrame(window : Long): Unit = {

    // glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

    // set the color of the quad (R,G,B,A)// set the color of the quad (R,G,B,A)

    glColor3f(0.5f, 0.5f, 1.0f)
    ECHandler.renderableEntities.foreach(renderEntity)

    glfwSwapBuffers(window); // swap the color buffers
  }

  def renderEntity(e: Entity): Unit = {
    //var r = ECHandler.getThisComponentOfE[RenderableCom](e)
    posAndThenModel(e, ECHandler.getThisComponentOfE[PositionCom](e))

  }

  private def posAndThenModel(e: Entity, pos : Option[PositionCom]): Unit = pos match {
    case Some(p) => modelAndThenRender(p, ECHandler.getThisComponentOfE[ModelCom](e))
    case None =>
  }

  private def modelAndThenRender(p : PositionCom, model : Option[ModelCom]): Unit = model match {
    case Some(m) => renderModel(m)
    case None =>
  }

  def renderModel(model : ModelCom): Unit = {
    // Bind to the VAO that has all the information about the quad vertices
    model.bind()
    model.enableVBO()

    // Draw the vertices
    GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.vertexCount)

    // Put everything back to default (deselect)
    model.disableVBO()
    model.unBind()
  }


  def dispose(): Unit = if (shader != null) shader.dispose()

}
