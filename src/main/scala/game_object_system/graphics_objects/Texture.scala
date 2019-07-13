package game_object_system.graphics_objects

import org.lwjgl.opengl.{GL11, GL13, GL30}

case class Texture (id : Int, w : Float, h : Float) {

  def bind(textureUnit : Int): Unit = {
    GL13.glActiveTexture(textureUnit)
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, id)
  }

//  def bindArray(textureUnit : Int): Unit = {
//    GL13.glActiveTexture(textureUnit)
//    GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, id)
//  }

  def dispose(): Unit = GL11.glDeleteTextures(id)
}
