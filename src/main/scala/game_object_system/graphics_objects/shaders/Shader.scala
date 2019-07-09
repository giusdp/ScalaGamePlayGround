package game_object_system.graphics_objects.shaders

import java.nio.FloatBuffer

import org.lwjgl.opengl.GL20

class Shader(program : Int) {

  val mvpLocation: Int = GL20.glGetUniformLocation(program, "mvp")

  def dispose(): Unit = {
    GL20.glDeleteProgram(program)
  }

  def use(): Unit = GL20.glUseProgram(program)

  def stop(): Unit = GL20.glUseProgram(0)

  def setMVP(fb : FloatBuffer): Unit = GL20.glUniformMatrix4fv(mvpLocation, false, fb)

}

object Shader {
  def apply(program: Int): Shader = new Shader(program)
}