package game_object_system.graphics_objects

trait Shape {
  val indices : Array[Int] = Array()

  val vertices : Array[Float] = Array()

  val tex_coords : Array[Float] = Array()
}

case class Quad() extends Shape {
  override val indices: Array[Int] = Array(
    0,1,3,
    3,1,2
  )

  override val vertices = Array(
    // Left bottom triangle
    -0.5f, 0.5f, 0f,
    -0.5f, -0.5f, 0f,
    0.5f, -0.5f, 0f,
    0.5f, 0.5f, 0f,
  )

  override val tex_coords: Array[Float] = Array(
    0,0,
    0,1,
    1,1,
    1,0
  )
}
