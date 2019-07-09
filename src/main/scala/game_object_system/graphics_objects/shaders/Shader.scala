package game_object_system.graphics_objects.shaders

import java.nio.FloatBuffer

import org.lwjgl.opengl.GL20

trait ShaderBase {
  def use()
  def stop()
  def loadMVP(fb : FloatBuffer)
}

case class Shader(program : Int) extends ShaderBase {

  val mvpLocation: Int = GL20.glGetUniformLocation(program, "mvp")

  def loadMVP(fb : FloatBuffer): Unit = GL20.glUniformMatrix4fv(mvpLocation, false, fb)

  def use(): Unit = GL20.glUseProgram(program)

  def stop(): Unit = GL20.glUseProgram(0)

  def dispose(): Unit = GL20.glDeleteProgram(program)

}

case class TileMapShader(shader: Shader) extends ShaderBase {

  override def use(): Unit = shader.use()

  override def stop(): Unit = shader.stop()

  override def loadMVP(fb: FloatBuffer): Unit = shader.loadMVP(fb)
}