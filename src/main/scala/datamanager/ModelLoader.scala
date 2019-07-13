package datamanager

import java.nio.{FloatBuffer, IntBuffer}

import game_object_system.graphics_objects.{Model, ModelPrimitive, RectModel}
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL11, GL15, GL20, GL30, GL33}

object ModelLoader {
  val VERTEX_LOCATION = 0
  val TEXTURE_LOCATION = 1
  val VERTEX_OFFSETS_LOCATION = 2
  val TEXTURE_OFFSETS_LOCATION = 3

  def loadModel(s : ModelPrimitive): Model = {
    val vao = GL30.glGenVertexArrays()
    GL30.glBindVertexArray(vao)

    val vbos = List(bindIndicesBuffer(s.indices), bindVertexBuffer(s.vertices), bindTexCoordBuffer(s.texCoords))

    GL30.glBindVertexArray(0)
    Model(vao, vbos, s.indices.length)
  }

  def loadModel(indices : Array[Int], vertices : Array[Float], texCoords : Array[Float]): Model = {
    val vao = GL30.glGenVertexArrays()
    GL30.glBindVertexArray(vao)

    val vbos = List(bindIndicesBuffer(indices), bindVertexBuffer(vertices), bindTexCoordBuffer(texCoords))

    GL30.glBindVertexArray(0)
    Model(vao, vbos, indices.length)
  }

  def loadVerticesOnlyModel(vertices : Array[Float]): Model = {
    val vao = GL30.glGenVertexArrays()
    GL30.glBindVertexArray(vao)

    val vbos = List(bindVertexBuffer(vertices))

    GL30.glBindVertexArray(0)
    Model(vao, vbos, vertices.length/3)
  }

  def loadTileMapModel(vertexOffsets : Array[Float], textureOffsets : Array[Float]): Model = {
    val tile = RectModel(0,0,2,2)
    val vao = GL30.glGenVertexArrays()
    GL30.glBindVertexArray(vao)
    val vbos = List(bindIndicesBuffer(tile.indices), bindVertexBuffer(tile.vertices), bindTexCoordBuffer(tile.texCoords),
      bindVertexOffsetsBuffer(vertexOffsets), bindTextureOffsetsBuffer(textureOffsets))
    GL30.glBindVertexArray(0)
    Model(vao, vbos, tile.vertices.length)
  }

  private def bindVertexBuffer(data : Array[Float]) = bindAttributeBuffer(VERTEX_LOCATION, 3, data)
  private def bindTexCoordBuffer(data: Array[Float]) = bindAttributeBuffer(TEXTURE_LOCATION, 2, data)
  private def bindVertexOffsetsBuffer(data: Array[Float]) = {
    val vbo = bindAttributeBuffer(VERTEX_OFFSETS_LOCATION, 3, data)
    GL33.glVertexAttribDivisor(VERTEX_OFFSETS_LOCATION,1)
    vbo
  }
  private def bindTextureOffsetsBuffer(data: Array[Float]) = {
    val vbo = bindAttributeBuffer(TEXTURE_OFFSETS_LOCATION, 2, data)
    GL33.glVertexAttribDivisor(TEXTURE_OFFSETS_LOCATION,1)
    vbo
  }

  private def bindAttributeBuffer(index : Int, coordSize : Int, data : Array[Float]) = {
    val vbo = GL15.glGenBuffers()
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, mkFloatBuffer(data), GL15.GL_STATIC_DRAW)
    GL20.glVertexAttribPointer(index, coordSize, GL11.GL_FLOAT, false, 0, 0)
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
