package game_engine.graphics

import org.lwjgl.glfw.GLFW.{glfwCreateWindow, glfwGetPrimaryMonitor, glfwGetVideoMode, glfwMakeContextCurrent, glfwSetWindowPos, glfwSetWindowTitle, glfwShowWindow, glfwSwapBuffers, glfwSwapInterval, glfwWindowShouldClose}
import org.lwjgl.glfw.GLFWVidMode
import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.opengl.GL11.{GL_COLOR_BUFFER_BIT, glClear, glClearColor}

object Window {

  var fpsCap = 60
  var width = 0
  var height = 0

  var window : Long = 0

  def createWindow(title : String, w : Int, h : Int) : Unit = {
    width = w
    height = h
    window = glfwCreateWindow(w, h, title, NULL, NULL)
    if (window == NULL) throw new RuntimeException("Failed to create the GLFW window.")

    val vidMode : GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())
    glfwSetWindowPos(window, (vidMode.width() - w) / 2, (vidMode.height() - h) / 2)

    // Make the OpenGL context current
    glfwMakeContextCurrent(window)
    // Enable v-sync
    glfwSwapInterval(1)

    glfwShowWindow(window)

  }

  def setSize(w : Int, h : Int): Unit = {
    width = w
    height = h
  }

  def setTitle(title : String) : Unit = glfwSetWindowTitle(Window.window, title)

  def shouldClose() : Boolean = glfwWindowShouldClose(window)

  def clearWindow(): Unit = {
    glClearColor(0.03f, 0, 0.1f, 0.5f)
    glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer
  }

  def swapBuffer(): Unit =glfwSwapBuffers(window); // swap the color buffers
}
