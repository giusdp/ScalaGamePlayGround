package game_object_system.graphics_objects.shaders

import java.nio.FloatBuffer

import org.lwjgl.opengl.GL20

case class Shader(program : Int){

  val mvpLocation: Int = GL20.glGetUniformLocation(program, "mvp")

  def loadMVP(fb : FloatBuffer): Unit = GL20.glUniformMatrix4fv(mvpLocation, false, fb)

  def use(): Unit = GL20.glUseProgram(program)

  def stop(): Unit = GL20.glUseProgram(0)

  def dispose(): Unit = GL20.glDeleteProgram(program)

}

case class TileMapShader(shader: Shader) {
  val tileWidthLocation: Int = GL20.glGetUniformLocation(shader.program, "tile_width")
  val tileHeightLocation: Int = GL20.glGetUniformLocation(shader.program, "tile_height")

  def use(): Unit = shader.use()

  def stop(): Unit = shader.stop()

  def loadMVP(fb: FloatBuffer): Unit = shader.loadMVP(fb)

  def loadTextureTileWidth(tw : Float): Unit = GL20.glUniform1f(tileWidthLocation, tw)
  def loadTextureTileHeight(th : Float): Unit = GL20.glUniform1f(tileHeightLocation, th)

}