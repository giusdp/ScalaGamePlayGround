package game_engine

import java.nio._

import datamanager.{EntityLoader, ShaderLoader}
import game_engine.graphics.{Renderer, ScreenConstants}
import game_object_system.ECHandler
import org.lwjgl.glfw.Callbacks._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw._
import org.lwjgl.opengl.GL
import org.lwjgl.system.MemoryStack._
import org.lwjgl.system.MemoryUtil._
import org.lwjgl.system._
import simulation.Simulation

import scala.annotation.tailrec


object Engine {

  def run(): Unit = {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set()

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if ( !glfwInit() )
      throw new IllegalStateException("Unable to initialize GLFW")

    val window = glfwCreateWindow(ScreenConstants.WIDTH.asInstanceOf[Int], ScreenConstants.HEIGHT.asInstanceOf[Int], ScreenConstants.TITLE, NULL, NULL)
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
        val renderer = new Renderer(s)

        /** Game started. */
        game_loop(window, renderer)

        /** Cleaning before exiting */
        renderer.dispose()
      case None => Console.err.println("Failed to create shader, aborted.")
    }

    ECHandler.disposeEntities()
    // Free the window callbacks and destroy the window
    glfwFreeCallbacks(window)
    glfwDestroyWindow(window)

    // Terminate GLFW and free the error callback
    glfwTerminate()
    glfwSetErrorCallback(null).free()
  }

  @tailrec
  def game_loop(window: Long, renderer : Renderer): Unit = {
    Input.tickInput()

    Simulation.update()

    renderer.renderFrame(window)

    if (! glfwWindowShouldClose(window))
      game_loop(window, renderer)

  }

}
