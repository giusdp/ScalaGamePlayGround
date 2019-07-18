package game_object_system.graphics_objects

import org.lwjgl.opengl.{GL11, GL13}

trait Texture {
  def bind(textureUnit : Int): Unit
  def dispose(): Unit
  def getWidth : Float
  def getHeight : Float
}

case class StaticTexture(id : Int, w : Float, h : Float) extends Texture {

  override def bind(textureUnit : Int): Unit = {
    GL13.glActiveTexture(textureUnit)
    GL11.glBindTexture(GL11.GL_TEXTURE_2D, id)
  }

  override def dispose(): Unit = GL11.glDeleteTextures(id)

  override def getWidth: Float = w
  override def getHeight: Float = h
}

case class TextureAtlas(id : Int, imageWidth : Float, imageHeight : Float, cellWidth : Float, cellHeight : Float) extends Texture {

  override def bind(textureUnit : Int): Unit = {
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

  def extractRegion(index : Int): Array[Array[Float]] = {
    val o = startingPointOfRegion(index)
    val u = o(0)
    val v = o(1)
    Array(
      Array(u, v),
      Array(u, v + regionHeight),
      Array(u + regionWidth, v + regionHeight),
      Array(u + regionWidth, v))
  }

  val regionWidth : Float = cellWidth/imageWidth
  val regionHeight: Float = cellHeight/imageHeight

  override def getWidth: Float = regionWidth
  override def getHeight: Float = regionHeight

  override def dispose(): Unit = GL11.glDeleteTextures(id)
}
