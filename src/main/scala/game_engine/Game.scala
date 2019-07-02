package game_engine

import com.badlogic.ashley.core.Entity
import datamanager.{EntityLoader, ShaderLoader, TextureLoader}
import game_engine.graphics.{RenderingSystem, Window}
import game_engine.movement.MovementSystem
import game_engine.pcg.DungeonGenerator
import game_engine.utils.Timer
import game_object_system.{ECEngine, PositionCom, RenderableCom}
import game_object_system.graphics_objects.{Camera, DungeonTileSet, Sprite}
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
    ECEngine.posMapper.get(player).z = 1
    ECEngine.engine.addEntity(player)

    Input.registerInput(player)

    val optionShader = ShaderLoader.loadShaderProgram("vs.glsl", "fs.glsl")

    try {
      val textureAtlas = TextureLoader.loadTilesTextureAtlas("tiles_dungeon.png", 16)
      val ts = DungeonTileSet(textureAtlas)
      val dungeon = DungeonGenerator.generateDungeon(ts)

      val sprite = Sprite(dungeon, textureAtlas.texture)
      val e = new Entity
      e.add(PositionCom(0,0,0))
      e.add(RenderableCom(sprite))
      ECEngine.engine.addEntity(e)


      val shader = optionShader.getOrElse(throw new RuntimeException("Failed to create shader, abort."))

      val renderer = new RenderingSystem(shader, 1)
      val movement = new MovementSystem(0)

      ECEngine.engine.addSystem(movement)
      ECEngine.engine.addSystem(renderer)

      /** Game started. */
      Timer.init()
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
