package game_object_system.graphics_objects

import org.lwjgl.opengl.GL20

class Shader(program : Int) {

  def dispose(): Unit = {
    GL20.glDeleteProgram(program)
  }

  def use(): Unit = GL20.glUseProgram(program)

  def stop(): Unit = GL20.glUseProgram(0)

}

object Shader {
  def apply(program: Int): Shader = new Shader(program)
}