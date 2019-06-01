package game_engine.graphics
import game_object_system.{ECHandler, Entity, PositionCom}
import org.lwjgl.glfw.GLFW._
import org.lwjgl.opengl.GL11._

object Renderer {

  def renderFrame(window : Long) = {

//    glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

    // set the color of the quad (R,G,B,A)// set the color of the quad (R,G,B,A)

    glColor3f(0.5f, 0.5f, 1.0f)
    ECHandler.renderableEntities.foreach(renderEntity)



    glfwSwapBuffers(window); // swap the color buffers
  }

  def renderEntity(e: Entity) = {
    //var r = ECHandler.getThisComponentOfE[RenderableCom](e)
    ECHandler.getThisComponentOfE[PositionCom](e) match {
      case Some(pos) => glBegin(GL_QUADS)
                        glVertex2d(pos.x * -0.5f, pos.y * 0.5f)
                        glVertex2d(pos.x * 0.5f, pos.y * 0.5f)
                        glVertex2d(pos.x * 0.5f, pos.y * -0.5f)
                        glVertex2d(pos.x * -0.5f, pos.y * -0.5f)
                        glEnd()
      case None =>
    }

  }

}
