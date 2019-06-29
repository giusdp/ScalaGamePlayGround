package game_engine.utils

import org.lwjgl.glfw.GLFW

object Timer {

  var lastFrame : Double = 0.0f

  def init(): Unit = lastFrame = getTime

  def getTime: Double = GLFW.glfwGetTime() / 1000.0

  def getDeltaTime: Float = {
    val time = getTime
    val delta = time - lastFrame
    lastFrame = time
    delta.toFloat
  }

}
