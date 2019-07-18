package game_object_system.graphics_objects.shaders

import java.nio.FloatBuffer

import org.lwjgl.opengl.GL20

case class Shader(program : Int) {
  val mvpLocation: Int = GL20.glGetUniformLocation(program, "mvp")

  def loadMVP(fb : FloatBuffer): Unit = GL20.glUniformMatrix4fv(mvpLocation, false, fb)

  def use(): Unit = GL20.glUseProgram(program)

  def stop(): Unit = GL20.glUseProgram(0)

  def dispose(): Unit = GL20.glDeleteProgram(program)
}

abstract class ShaderInterface(shader:Shader) {
  def loadMVP(fb : FloatBuffer): Unit = shader.loadMVP(fb)
  def use(): Unit = shader.use()
  def stop(): Unit = shader.stop()
  def dispose(): Unit = shader.dispose()
}

case class AnimatedSpriteShader(shader: Shader) extends ShaderInterface(shader) {
  val spriteWidthLocation: Int = GL20.glGetUniformLocation(shader.program,"sprite_width")
  val spriteHeightLocation: Int = GL20.glGetUniformLocation(shader.program, "sprite_height")

  def loadSpriteWidth(tw : Float): Unit = GL20.glUniform1f(spriteWidthLocation, tw)
  def loadSpriteHeight(th : Float): Unit = GL20.glUniform1f(spriteHeightLocation, th)

  def loadTexCoords(uvs : Array[Array[Float]]): Unit = loadTexCoords(uvs(0),uvs(1),uvs(2),uvs(3))

  def loadTexCoords(tc0 : Array[Float], tc1 : Array[Float], tc2 : Array[Float], tc3 : Array[Float]): Unit = {
    val loc0: Int = GL20.glGetUniformLocation(shader.program,"tex_offsets[0]")
    val loc1: Int = GL20.glGetUniformLocation(shader.program,"tex_offsets[1]")
    val loc2: Int = GL20.glGetUniformLocation(shader.program,"tex_offsets[2]")
    val loc3: Int = GL20.glGetUniformLocation(shader.program,"tex_offsets[3]")

    GL20.glUniform2fv(loc0, tc0)
    GL20.glUniform2fv(loc1, tc1)
    GL20.glUniform2fv(loc2, tc2)
    GL20.glUniform2fv(loc3, tc3)
  }
}

case class TileMapShader(shader: Shader) extends ShaderInterface(shader) {
  val tileWidthLocation: Int = GL20.glGetUniformLocation(shader.program, "tile_width")
  val tileHeightLocation: Int = GL20.glGetUniformLocation(shader.program, "tile_height")
  def loadTextureTileWidth(tw : Float): Unit = GL20.glUniform1f(tileWidthLocation, tw)
  def loadTextureTileHeight(th : Float): Unit = GL20.glUniform1f(tileHeightLocation, th)
}