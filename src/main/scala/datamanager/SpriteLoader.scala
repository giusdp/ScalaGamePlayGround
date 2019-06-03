package datamanager

import java.nio.{FloatBuffer, IntBuffer}

import game_object_system.ModelCom
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL11, GL15, GL20, GL30}

object SpriteLoader {

  def loadSprite(vertices: Array[Float], indices : Array[Int]) = {
    val vao = GL30.glGenVertexArrays()
    GL30.glBindVertexArray(vao)

    val vbos = List(bindIndicesBuffer(indices), bindVertexBuffer(vertices))

    GL30.glBindVertexArray(0)
    ModelCom(vao, vbos, indices.length)
  }

  private def bindVertexBuffer(vertices : Array[Float]) = {
    val vertVBO = GL15.glGenBuffers()
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertVBO)
    val vBuffer = mkFloatBuffer(vertices)
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vBuffer, GL15.GL_STATIC_DRAW)
    GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0)
    vertVBO
  }

  private def bindIndicesBuffer(indices : Array[Int])  = {
    val indVBO = GL15.glGenBuffers()
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indVBO)
    val iBuffer = mkIntBuffer(indices)
    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, iBuffer, GL15.GL_STATIC_DRAW)
    GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0)
    indVBO
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
