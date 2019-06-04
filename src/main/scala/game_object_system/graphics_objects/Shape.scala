package game_object_system.graphics_objects

trait Shape {
  val vertices : Array[Float] = Array()

  val indices : Array[Int] = Array()
}

case class Quad() extends Shape {
  override val vertices = Array(
    // Left bottom triangle
    -0.5f, 0.5f, 0f,
    -0.5f, -0.5f, 0f,
    0.5f, -0.5f, 0f,
    0.5f, 0.5f, 0f,
  )

  override val indices: Array[Int] = Array(
    0,1,3,
    3,1,2
  )
}
