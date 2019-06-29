package game_engine

import datamanager.{EntityLoader, ShaderLoader}
import game_engine.graphics.{RenderingSystem, Window}
import game_engine.movement.MovementSystem
import game_engine.utils.Timer
import game_object_system.{ECEngine, Globals}
import org.lwjgl.glfw.Callbacks._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw._
import org.lwjgl.opengl.GL

import scala.annotation.tailrec


object Game {

  var canRender : Boolean = false

  val frameCap : Float = 1.0f/60.0f
  var frameTime : Float = 0
  var frames = 0

  def run(): Unit = {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set()

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if ( !glfwInit() )
      throw new IllegalStateException("Unable to initialize GLFW")

    Window.createWindow(Globals.TITLE, Globals.WIDTH.asInstanceOf[Int], Globals.HEIGHT.asInstanceOf[Int])

    GL.createCapabilities()


    /** Initialization done, loading entities */
    val player = EntityLoader.createEntitiesFromJSON("player.json").head
    ECEngine.engine.addEntity(player)
    InputHandler.registerInput(Window.window, player)

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
    Timer.init()
    gameLoop()

    /** Cleaning before exiting */
    renderer.dispose()
    cleanUp()

    @tailrec
    def gameLoop(): Unit = {
      canRender = false
      val deltaTime = Timer.getDeltaTime
      Timer.unprocessedTime += deltaTime
      frameTime += deltaTime

      val unprocessedTimeLeft = processPassedTime(Timer.unprocessedTime, deltaTime)
      Timer.unprocessedTime = unprocessedTimeLeft

      if (canRender) {
        renderer.update(deltaTime)
        frames += 1
      }

      if (! Window.shouldClose()) gameLoop()
    }
  }

  @tailrec
  private def processPassedTime(unprocessedTime : Float, deltaTime : Float) : Float = {
    if (unprocessedTime >= frameCap) {
      glfwPollEvents()

      ECEngine.engine.update(deltaTime)

      // FPS Counter
      if (frameTime >= 1.0f) {
        frameTime = 0
        println("FPS: " + frames.toString)
        frames = 0
      } else {}
      processPassedTime(unprocessedTime - frameCap, deltaTime)
    }
    else {
      canRender = true
      unprocessedTime
    }
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
