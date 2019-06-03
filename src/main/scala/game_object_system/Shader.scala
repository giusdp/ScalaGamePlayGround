package game_object_system

import org.lwjgl.opengl.GL20

trait Shader{
  def useShader()
  def stopShader()
  def dispose()
}
case class StaticShader(program : Int) extends Shader {

  override def useShader(): Unit = GL20.glUseProgram(program)

  override def stopShader(): Unit = GL20.glUseProgram(0)

  override def dispose(): Unit = {
    stopShader()
    GL20.glDeleteProgram(program)
  }

}

object StaticShader {
  def apply(program: Int): StaticShader = new StaticShader(program)
}