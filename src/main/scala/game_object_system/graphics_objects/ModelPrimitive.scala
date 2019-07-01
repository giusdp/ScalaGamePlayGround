package game_object_system.graphics_objects

trait ModelPrimitive {
  val indices : Array[Int] = Array()

  val vertices : Array[Float] = Array()

  val texCoords : Array[Float] = Array()
}

case class ModelRect(x : Float, y : Float, w : Float, h : Float) extends ModelPrimitive {
  override val indices: Array[Int] = Array(
    0,1,3,
    3,1,2
  )

  override val vertices = Array(
    x - (w/2), y + (h/2), 0f,
    x - (w/2) , y - (h/2), 0f,
    x + (w/2), y - (h/2), 0f,
    x + (w/2), y + (h/2), 0f,
  )

  override val texCoords: Array[Float] = Array(
    0,0,
    0,1,
    1,1,
    1,0
  )
}

case class CustomTextureRect(x : Float, y : Float, w : Float, h : Float, texs : Array[Float]) extends ModelPrimitive {
  override val indices: Array[Int] = Array(
    0,1,3,
    3,1,2
  )

  override val vertices = Array(
    x - (w/2), y + (h/2), 0f,
    x - (w/2) , y - (h/2), 0f,
    x + (w/2), y - (h/2), 0f,
    x + (w/2), y + (h/2), 0f,
  )

  override val texCoords: Array[Float] = texs
}
