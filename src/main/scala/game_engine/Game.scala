package game_engine

import com.badlogic.ashley.core.Entity
import datamanager.{EntityLoader, ShaderLoader, TMXLoader}
import game_engine.graphics.{RenderingSystem, TileMapRenderer, Window}
import game_engine.movement.MovementSystem
import game_engine.utils.Timer
import game_object_system.graphics_objects.Camera
import game_object_system.graphics_objects.shaders.TileMapShader
import game_object_system.{ECEngine, TileMapCom}
import org.lwjgl.glfw.Callbacks._
import org.lwjgl.glfw.GLFW._
import org.lwjgl.glfw._
import org.lwjgl.opengl.{GL, GL11}

import scala.annotation.tailrec

object Game {
  var SCREEN_WIDTH = 1024
  var SCREEN_HEIGHT = 720

  def run(): Unit = {

    val t0 = System.nanoTime()

    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set()

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if ( !glfwInit() ) throw new IllegalStateException("Unable to initialize GLFW")

    Window.createWindow("SCExp", SCREEN_WIDTH, SCREEN_HEIGHT)
    Camera.setViewSize(Window.width, Window.height)

    GL.createCapabilities()

    GL11.glEnable(GL11.GL_BLEND)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)

    /** Initialization done, loading entities */
    val player = EntityLoader.createEntitiesFromJSON("player.json").head
    //ECEngine.posMapper.get(player).z = 1
    ECEngine.engine.addEntity(player)

    Input.registerInput(player)

    val optionSpriteShader = ShaderLoader.loadShaderProgram("sprite_shaders/vs.glsl",
      "sprite_shaders/fs.glsl")
    val optionTileMapShader = ShaderLoader.loadShaderProgram("tilemap_shaders/vs.glsl",
      "tilemap_shaders/fs.glsl")

    try {
      val tmxMap = TMXLoader.parseTMX("s1.tmx")
//      tmxMap.scaleMap(128)
      tmxMap.translateMap(-300, 500, 0)
      tmxMap.scaleMap(10)

      val mapEntity = new Entity()
      mapEntity.add(TileMapCom(tmxMap))
      ECEngine.engine.addEntity(mapEntity)

      val spriteShader = optionSpriteShader.getOrElse(throw new RuntimeException("Failed to create sprite shader, abort."))
      val tileMapShader = TileMapShader(optionTileMapShader.getOrElse(throw new RuntimeException("Failed to create tilemap shader, abort.")))

      val mapRenderer = new TileMapRenderer(tileMapShader, 1)
      val renderer = new RenderingSystem(spriteShader, 2)
      val movement = new MovementSystem(0)

      ECEngine.engine.addSystem(movement)
      ECEngine.engine.addSystem(renderer)
      ECEngine.engine.addSystem(mapRenderer)

      /** Game started. */
      Timer.init()
      val t1 = System.nanoTime()
      println("Time elapsed to initialize everything (before entering game loop): " + (t1 - t0) / 1000000 + "ms")

      gameLoop()

      /** Cleaning before exiting */
      renderer.dispose()
    }
    catch{
        case e : Exception => e.printStackTrace()
      }
    finally {
      cleanUp()
    }
  }

  val fpsCap : Int = 60
  val frameTime : Double = 1000.0/fpsCap.toDouble // 16 ms each frame
  var lastFPSTime : Double = 0
  var fps : Int = 0

  @tailrec
  def gameLoop(): Unit = {
    val deltaTime = Timer.getDeltaTime

    lastFPSTime += deltaTime
    fps += 1
    if (lastFPSTime >= 1.0){
      Window.setTitle("FPS: " + fps)
      lastFPSTime = 0
      fps = 0
    }

    glfwPollEvents()
    Window.clearWindow()
    ECEngine.engine.update(deltaTime)
    Window.swapBuffer()

    try{Thread.sleep((Timer.lastFrame - Timer.getTime + frameTime).toLong)}
    catch {case e: Exception => Console.err.println(e.getMessage)}

    if (!Window.shouldClose()) gameLoop()
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
