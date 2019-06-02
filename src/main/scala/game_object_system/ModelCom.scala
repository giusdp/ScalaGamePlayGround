package game_object_system

import org.lwjgl.opengl.GL30

case class ModelCom() extends Component {

  var VAO: Int = GL30.glGenVertexArrays()

  def bind(): Unit = GL30.glBindVertexArray(VAO)
  def unBind(): Unit = GL30.glBindVertexArray(0)


  val model = List(
    // Left bottom triangle
    -0.5f, 0.5f, 0f,
    -0.5f, -0.5f, 0f,
    0.5f, -0.5f, 0f,
    // Right top triangle
    0.5f, -0.5f, 0f,
    0.5f, 0.5f, 0f,
    -0.5f, 0.5f, 0f
  )
}