package game_object_system.graphics_objects.shaders

import java.nio.FloatBuffer

import org.lwjgl.opengl.GL20

case class Shader(program : Int) {

  val mvpLocation: Int = GL20.glGetUniformLocation(program, "mvp")

  def loadMVP(fb : FloatBuffer): Unit = GL20.glUniformMatrix4fv(mvpLocation, false, fb)

  def use(): Unit = GL20.glUseProgram(program)

  def stop(): Unit = GL20.glUseProgram(0)

  def dispose(): Unit = {
    GL20.glDeleteProgram(program)
  }

}

class TileMapShader(program : Int) extends Shader(program) {

}