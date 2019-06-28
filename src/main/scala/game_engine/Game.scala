package game_engine

import java.nio._

import datamanager.{EntityLoader, ShaderLoader}
import game_engine.Movement.MovementSystem
import game_engine.graphics.RenderingSystem
import game_object_system.{ECEngine, Globals}
import org.lwjgl.glfw.Callbacks._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw._
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.{GL_COLOR_BUFFER_BIT, glClear, glClearColor}
import org.lwjgl.system.MemoryStack._
import org.lwjgl.system.MemoryUtil._
import org.lwjgl.system._
import simulation.Simulation

import scala.annotation.tailrec


object Game {

  def run(): Unit = {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set()

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if ( !glfwInit() )
      throw new IllegalStateException("Unable to initialize GLFW")

    val window = glfwCreateWindow(Globals.WIDTH.asInstanceOf[Int], Globals.HEIGHT.asInstanceOf[Int], Globals.TITLE, NULL, NULL)
    if (window == NULL) throw new RuntimeException("Failed to create the GLFW window")

    try {

      val stack: MemoryStack = stackPush()
      val pWidth: IntBuffer = stack.mallocInt(1); // int*
      val pHeight: IntBuffer = stack.mallocInt(1); // int*

      // Get the window size passed to glfwCreateWindow
      glfwGetWindowSize(window, pWidth, pHeight)

      // Get the resolution of the primary monitor
      val vidmode: GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())

      // Center the window
      glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2)

      stackPop()
    }
    catch  {
      case e : Exception => Console.err.println("Error handling window properties when starting engine: " + e.getMessage)
    }

    // Make the OpenGL context current
    glfwMakeContextCurrent(window)
    // Enable v-sync
    glfwSwapInterval(1)

    // Make the window visible
    glfwShowWindow(window)
    GL.createCapabilities()


    /** Initialization done, loading entities */
    val player = EntityLoader.createEntitiesFromJSON("player.json").head
    Input.registerInput(window, player)

    val optionShader = ShaderLoader.loadShaderProgram("vs.glsl", "fs.glsl")
    optionShader match {
      case Some(s) =>
        val renderer = new RenderingSystem(s, 1)

        val movement = new MovementSystem(0)

        ECEngine.engine.addSystem(movement)
        ECEngine.engine.addSystem(renderer)


        /** Game started. */
        game_loop(window)

        /** Cleaning before exiting */
        renderer.dispose()

      case None => Console.err.println("Failed to create shader, abort.")
    }
    // Free the window callbacks and destroy the window
    glfwFreeCallbacks(window)
    glfwDestroyWindow(window)

    // Terminate GLFW and free the error callback
    glfwTerminate()
    glfwSetErrorCallback(null).free()
  }

  @tailrec
  def game_loop(window: Long): Unit = {
    Input.tickInput()

    Simulation.update()

    glClearColor(1.0f, 0.0f, 0.0f, 0.0f)
    glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

    ECEngine.engine.update(1)

    glfwSwapBuffers(window); // swap the color buffers

    if (! glfwWindowShouldClose(window)) game_loop(window)
  }
}
