package game_object_system

import org.lwjgl.opengl.{GL15, GL20, GL30}


case class ModelCom(vao : Int, vbos : List[Int], vCount : Int) extends Component {

  def dispose(): Unit = {
    GL30.glBindVertexArray(0)

    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0)
    // Delete the VBO
    vbos.foreach(vbo => GL15.glDeleteBuffers(vbo))

    // Delete the VAO
    GL30.glDeleteVertexArrays(vao)
  }

}

object ModelCom {

  def apply(vao: Int, vbos: List[Int], vCount: Int): ModelCom = new ModelCom(vao, vbos, vCount)

}