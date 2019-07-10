package game_object_system.graphics_objects

import org.joml.Matrix4f
import org.lwjgl.opengl.{GL15, GL30}


class Model(val vao : Int, vbos : List[Int], val vCount : Int){

  val model_matrix: Matrix4f = new Matrix4f()

  def translate(x : Float, y: Float, z : Float):Unit = model_matrix.identity().translate(x, y, z)
  def scale(x : Float, y: Float, z : Float):Unit = model_matrix.identity().scale(x, y, z)
  def rotate(angle : Float, x : Float, y: Float, z : Float):Unit = model_matrix.identity().rotate(angle, x, y, z)

  def bindModel(): Unit = GL30.glBindVertexArray(vao)
  def unBindModel(): Unit = GL30.glBindVertexArray(0)

  def dispose(): Unit = {
    // Delete the VBO
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0)
    vbos.foreach(vbo => GL15.glDeleteBuffers(vbo))

    // Delete the VAO
    GL30.glBindVertexArray(0)
    GL30.glDeleteVertexArrays(vao)
  }

}

object Model {
  def apply(vao: Int, vbos: List[Int], vCount: Int): Model = new Model(vao, vbos, vCount)
}