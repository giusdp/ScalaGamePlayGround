package datamanager

import java.io.ByteArrayInputStream
import java.nio.ByteBuffer
import java.nio.file.{Files, Paths}

import datamanager.Resource.RES_DIR
import de.matthiasmann.twl.utils.PNGDecoder
import game_object_system.graphics_objects.{StaticTexture, TextureAtlas}
import org.lwjgl.opengl.{GL11, GL12, GL30}

object TextureLoader {

  def loadTextureAtlas(filename: String, tileWidth: Int, tileHeight : Int): TextureAtlas = {
    val t = TextureLoader.loadTexture(filename)
      .getOrElse(throw new RuntimeException("Failed to load texture atlas: " + filename + "."))
    TextureAtlas(t.id, t.w, t.h, tileWidth, tileHeight)
  }

  def loadTexture(fileName: String): Option[StaticTexture] = {
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
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE)
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE)

      //upload texture
      GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth, decoder.getHeight,
        0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer)

      // Generate Mip Map
      GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D)

      Some(StaticTexture(id, decoder.getWidth, decoder.getHeight))
    }
    catch {
      case _: Exception => Console.err.println("Error while loading texture: " + fileName); None
    }
  }
}

