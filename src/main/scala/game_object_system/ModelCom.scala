package game_object_system

import org.lwjgl.BufferUtils
import org.lwjgl.opengl.{GL11, GL15, GL20, GL30}

trait Model{
  def bind()
  def unBind()
  def enableVBO()
  def disableVBO()
  def setup()
  def dispose()
}

case class ModelCom() extends Component with Model {

  var vao: Int = _
  var vbo : Int = _

  def bind(): Unit = GL30.glBindVertexArray(vao)
  def unBind(): Unit = GL30.glBindVertexArray(0)

  val vertexCount = 6
  val model = Array(
    // Left bottom triangle
    -0.5f, 0.5f, 0f,
    -0.5f, -0.5f, 0f,
    0.5f, -0.5f, 0f,
    // Right top triangle
    0.5f, -0.5f, 0f,
    0.5f, 0.5f, 0f,
    -0.5f, 0.5f, 0f
  )

  override def setup(): Unit = {
    vao = GL30.glGenVertexArrays()
    bind()

    vbo = GL15.glGenBuffers()
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)

    val buffer = BufferUtils.createFloatBuffer(model.length)
    buffer.put(model)
    buffer.flip()

    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW)
    GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0)
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)

    unBind()
  }

  override def dispose(): Unit = {
    disableVBO()
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
    // Delete the VBO
    GL15.glDeleteBuffers(vbo)

    unBind()
    // Delete the VAO
    GL30.glDeleteVertexArrays(vao)
  }

  override def enableVBO(): Unit = GL20.glEnableVertexAttribArray(vbo)

  override def disableVBO(): Unit = GL20.glDisableVertexAttribArray(0)

  override def compare(that: Component): Int = 0
}

object ModelCom {

  def apply(): ModelCom = {
    val m = new ModelCom()
    m.setup()
    m
  }

}