package game_object_system.graphics_objects

import org.lwjgl.opengl.{GL11, GL13}

case class Texture (id : Int, w : Float, h : Float) {

  def bind(textureUnit : Int) = {
    GL13.glActiveTexture(textureUnit)
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, id)
  }

  def dispose(): Unit = GL11.glDeleteTextures(id)
}
