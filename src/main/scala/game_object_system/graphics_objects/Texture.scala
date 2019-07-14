package game_object_system.graphics_objects

import org.lwjgl.opengl.{GL11, GL13}

trait Texture {
  def bind(textureUnit : Int): Unit
  def dispose(): Unit
}

case class StaticTexture(id : Int, w : Float, h : Float) extends Texture {

  def bind(textureUnit : Int): Unit = {
    GL13.glActiveTexture(textureUnit)
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, id)
  }

  def dispose(): Unit = GL11.glDeleteTextures(id)
}

case class TextureAtlas(id : Int, imageWidth : Float, imageHeight : Float, cellWidth : Float, cellHeight : Float) extends Texture {

  def bind(textureUnit : Int): Unit = {
    GL13.glActiveTexture(textureUnit)
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, id)
  }

  def startingPointOfRegion(index : Int) : Array[Float] = {
    val i: Int = index - 1
    val columns: Float = imageWidth/cellWidth
    val u: Float = (i % columns) * regionWidth
    val v: Float = Math.floor(i / columns).toFloat * regionHeight
    Array(u, v)
  }

  val regionWidth : Float = cellWidth/imageWidth
  val regionHeight: Float = cellHeight/imageHeight

  def dispose(): Unit = GL11.glDeleteTextures(id)
}
