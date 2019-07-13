package datamanager

import java.io.ByteArrayInputStream
import java.nio.ByteBuffer
import java.nio.file.{Files, Paths}

import datamanager.Resource.RES_DIR
import de.matthiasmann.twl.utils.PNGDecoder
import game_object_system.graphics_objects.{Texture, TextureAtlas}
import org.lwjgl.opengl.{GL11, GL12, GL30}

object TextureLoader {

  def loadTextureAtlas(filename: String, tileCount : Int, tileWidth: Int, tileHeight : Int): TextureAtlas = {
    val t = TextureLoader.loadTexture(filename)
      .getOrElse(throw new RuntimeException("Failed to load texture atlas: " + filename + "."))
    TextureAtlas(t, tileWidth, tileHeight)
  }

  def loadTexture(fileName: String): Option[Texture] = {
    try {
      val byteArray = Files.readAllBytes(Paths.get(RES_DIR + fileName))
      //load png file
      val decoder: PNGDecoder = new PNGDecoder(new ByteArrayInputStream(byteArray))

      //create a byte buffer big enough to store RGBA values
      val buffer: ByteBuffer = ByteBuffer.allocateDirect(4 * decoder.getWidth * decoder.getHeight)

      //decode
      decoder.decode(buffer, decoder.getWidth * 4, PNGDecoder.Format.RGBA)

      //flip the buffer so its ready to read
      buffer.flip()

      //create a texture
      val id = GL11.glGenTextures()

      //bind the texture
      GL11.glBindTexture(GL11.GL_TEXTURE_2D, id)

      //tell opengl how to unpack bytes
      GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1)

      //set the texture parameters, can be GL_LINEAR or GL_NEAREST
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)

      //upload texture
      GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth, decoder.getHeight,
        0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer)

      // Generate Mip Map
      GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D)

      Some(Texture(id, decoder.getWidth, decoder.getHeight))
    }
    catch {
      case _: Exception => Console.err.println("Error while loading texture: " + fileName); None
    }
  }

  def loadArrayTexture(fileName: String, imageCount : Int, width : Int, height : Int): Option[Texture] = {
    try {
      val byteArray = Files.readAllBytes(Paths.get(RES_DIR + fileName))
      //load png file
      val decoder: PNGDecoder = new PNGDecoder(new ByteArrayInputStream(byteArray))

      //create a byte buffer big enough to store RGBA values
      val buffer: ByteBuffer = ByteBuffer.wrap(new Array(4 * decoder.getWidth * decoder.getHeight))

      //decode
      decoder.decode(buffer, decoder.getWidth * 4, PNGDecoder.Format.RGBA)
      //flip the buffer so its ready to read
      buffer.flip()

//      println(buffer.array().toList)

      //create a texture
      val id = GL11.glGenTextures()
//      GL13.glActiveTexture(0)

      //bind the texture
      GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, id)

      //tell opengl how to unpack bytes
      GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1)

      GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR)
      GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR)

      //      GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY,GL11.GL_TEXTURE_WRAP_S,GL11.GL_REPEAT)
      //      GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY,GL11.GL_TEXTURE_WRAP_T,GL11.GL_REPEAT)

      GL12.glTexImage3D(GL30.GL_TEXTURE_2D_ARRAY, 1, GL11.GL_RGBA8, decoder.getWidth, decoder.getHeight, 1, 0, GL11.GL_RGBA8, GL11.GL_UNSIGNED_BYTE, 0)
      //      GL42.glTexStorage3D(GL30.GL_TEXTURE_2D_ARRAY, 1, GL11.GL_RGBA8, decoder.getWidth, decoder.getHeight, 1)
      //      GL12.glTexImage3D(GL30.GL_TEXTURE_2D_ARRAY, 0, GL11.GL_RGBA8, decoder.getWidth, decoder.getHeight, 1,
      //        0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE,  0)

      GL12.glTexSubImage3D(GL30.GL_TEXTURE_2D_ARRAY, 0, 0, 0, 0,
      decoder.getWidth, decoder.getHeight, 1,GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer)


      GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D_ARRAY)

      //upload array text
      // Generate Mip Map

      Some(Texture(id, decoder.getWidth, decoder.getHeight))
    }
    catch {
      case e: Exception =>
        Console.err.println("Error while loading texture: " + fileName)
        Console.err.println(e.printStackTrace())
        None
    }
  }
}

