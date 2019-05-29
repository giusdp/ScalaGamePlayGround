package game_engine

import game_object_system.{ECHandler, InputCom, MovableCom, PositionCom}
import org.lwjgl.glfw.GLFW.{glfwCreateWindow, glfwInit, glfwWindowShouldClose}
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryUtil.NULL
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

    val window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL)
    if (window == NULL) throw new RuntimeException("Failed to create the GLFW window")

    // TODO: togliere da qui e generare components con file yaml e un data manager
    var player = ECHandler.spawnEntity()
    ECHandler.addComponent(player, PositionCom(0,0))
    ECHandler.addComponent(player, MovableCom(10))
    ECHandler.addComponent(player, InputCom())

    Input.registerInput(window)

    // TODO: togliere da qui e generare components con file yaml e un data manager

    game_loop(window)
  }

  @tailrec
  def game_loop(window: Long): Unit = {

    Input.tickInput()

    Simulation.update()

    if (! glfwWindowShouldClose(window))
      game_loop(window)

  }

}
