package game_engine.graphics

import org.lwjgl.glfw.GLFW.{glfwCreateWindow, glfwMakeContextCurrent, glfwSetWindowPos, glfwShowWindow, glfwSwapBuffers,
  glfwSwapInterval, glfwWindowShouldClose, glfwGetVideoMode, glfwGetPrimaryMonitor}
import org.lwjgl.glfw.GLFWVidMode
import org.lwjgl.system.MemoryUtil.NULL
import org.lwjgl.opengl.GL11.{GL_COLOR_BUFFER_BIT, glClear, glClearColor}


object Window {

  var fpsCap = 60

  var window : Long = 0

  def createWindow(title : String, width : Int, height : Int) : Unit = {
    window = glfwCreateWindow(width, height, title, NULL, NULL)
    if (window == NULL) throw new RuntimeException("Failed to create the GLFW window.")

    val vidMode : GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())
    glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2)

    // Make the OpenGL context current
    glfwMakeContextCurrent(window)
    // Enable v-sync
    glfwSwapInterval(1)

    glfwShowWindow(window)

  }

  def setSize(w : Int, h : Int) = ???
  def shouldClose() : Boolean = glfwWindowShouldClose(window)

  def clearWindow(): Unit = {
    glClearColor(1.0f, 0.5f, 0.0f, 0.0f)
    glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer
  }

  def swapBuffer(): Unit =glfwSwapBuffers(window); // swap the color buffers
}
