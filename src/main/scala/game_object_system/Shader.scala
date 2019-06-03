package game_object_system

import org.lwjgl.opengl.GL20

class Shader(program : Int) {

  def dispose(): Unit = {
    GL20.glDeleteProgram(program)
  }

  def getProgram: Int = program

}

object Shader {
  def apply(program: Int): Shader = new Shader(program)
}