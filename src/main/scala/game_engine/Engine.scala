package game_engine

import org.lwjgl.glfw.GLFW.{glfwCreateWindow, glfwInit, glfwWindowShouldClose}
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryUtil.NULL
import state_system.{PositionCom, SpawnedEntities}

import scala.annotation.tailrec

object Engine {

  def run(): Unit = {
    // Setup an error callback. The default implementation
    // will print the error message in System.err.
    GLFWErrorCallback.createPrint(System.err).set()

    // Initialize GLFW. Most GLFW functions will not work before doing this.
    if ( !glfwInit() )
      throw new IllegalStateException("Unable to initialize GLFW")

    val window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL)
    if (window == NULL) throw new RuntimeException("Failed to create the GLFW window")

    Input.setupCallbacks(window)

    SpawnedEntities.entities.head.addComponent(PositionCom(0, 0)) // TODO: togliere da qui e generare components con file yaml e un data manager
    game_loop(window)
  }

  @tailrec
  def game_loop(window: Long): Unit = {
    Input.tickInput()



    if (! glfwWindowShouldClose(window))
      game_loop(window)

  }

}
