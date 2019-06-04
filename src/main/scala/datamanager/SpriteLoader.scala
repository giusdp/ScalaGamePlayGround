package datamanager

import java.nio.{FloatBuffer, IntBuffer}

import game_object_system.graphics_objects.{Model, Shape, SpriteCom, Texture}
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL11, GL15, GL20, GL30}

object SpriteLoader {
  val VERTEX_LOCATION = 0
  val TEXTURE_LOCATION = 1
//  val VERTEX_LOCATION = 0

  def loadSprite(s : Shape, imgFileName : String): SpriteCom = {
    val vao = GL30.glGenVertexArrays()
    GL30.glBindVertexArray(vao)

    val vbos = List(bindVertexBuffer(s.vertices), bindIndicesBuffer(s.indices))

    GL30.glBindVertexArray(0)
    SpriteCom(Model(vao, vbos, s.indices.length), TextureLoader.loadTexture(imgFileName))
  }

  private def bindVertexBuffer(data : Array[Float]) = bindAttributeBuffer(VERTEX_LOCATION, data)

  private def bindAttributeBuffer(index : Int, data : Array[Float]) = {
    val vbo = GL15.glGenBuffers()
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, mkFloatBuffer(data), GL15.GL_STATIC_DRAW)
    GL20.glVertexAttribPointer(index, 3, GL11.GL_FLOAT, false, 0, 0)
    GL20.glEnableVertexAttribArray(index)
    vbo
  }

  private def bindIndicesBuffer(indices : Array[Int])  = {
    val vbo = GL15.glGenBuffers()
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo)
    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, mkIntBuffer(indices), GL15.GL_STATIC_DRAW)
    vbo
  }

  private def mkFloatBuffer(data: Array[Float]): FloatBuffer = {
    val b = BufferUtils.createFloatBuffer(data.length)
    b.put(data)
    b.flip()
  }
  private def mkIntBuffer(data: Array[Int]): IntBuffer = {
    val b = BufferUtils.createIntBuffer(data.length)
    b.put(data)
    b.flip()
  }

}
