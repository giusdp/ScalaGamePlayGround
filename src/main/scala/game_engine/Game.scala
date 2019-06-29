package game_engine

import datamanager.{EntityLoader, ShaderLoader}
import game_engine.graphics.{RenderingSystem, Window}
import game_engine.movement.MovementSystem
import game_object_system.ECEngine
import game_object_system.graphics_objects.Camera
import org.lwjgl.glfw.Callbacks._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw._
import org.lwjgl.opengl.GL

import scala.annotation.tailrec


object Game {


  def run(): Unit = {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set()

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if ( !glfwInit() )
      throw new IllegalStateException("Unable to initialize GLFW")

    Window.createWindow("SCExp", 800, 480)
    Camera.setViewSize(Window.width, Window.height)

    GL.createCapabilities()

    /** Initialization done, loading entities */
    val player = EntityLoader.createEntitiesFromJSON("player.json").head
    ECEngine.engine.addEntity(player)

    InputHandler.registerInput(player)

    val optionShader = ShaderLoader.loadShaderProgram("vs.glsl", "fs.glsl")

    val shader = optionShader.getOrElse({
      cleanUp()
      throw new RuntimeException("Failed to create shader, abort.")
    })

    val renderer = new RenderingSystem(shader, 1)
    val movement = new MovementSystem(0)

    ECEngine.engine.addSystem(movement)
    ECEngine.engine.addSystem(renderer)

    /** Game started. */
    gameLoop()

    /** Cleaning before exiting */
    renderer.dispose()
    cleanUp()

  }

  @tailrec
  def gameLoop(): Unit = {
    glfwPollEvents()
    Window.clearWindow()
    ECEngine.engine.update(1)
    Window.swapBuffer()

    if (! Window.shouldClose()) gameLoop()
  }

  def cleanUp(): Unit = {
    // Free the window callbacks and destroy the window
    glfwFreeCallbacks(Window.window)
    glfwDestroyWindow(Window.window)

    // Terminate GLFW and free the error callback
    glfwTerminate()
    glfwSetErrorCallback(null).free()
  }
}
