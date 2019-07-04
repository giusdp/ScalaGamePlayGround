package game_object_system.graphics_objects

case class TextureAtlas(texture : Texture, cellWidth : Float, cellHeight : Float) {

//  def makeTile(index : Int) : Tile = {
//    val tw : Float = cellWidth/imageWidth
//    val th = cellHeight/imageHeight
//    val columns = imageWidth/cellWidth
//    val tx = (index % columns) / tw
//    val ty = (index / columns + 1) * th
//    val tc = Array(
//      tx, ty,
//      tx + tw, ty,
//      tx + tw, ty + th,
//      tx, ty + th
//    )
//    Tile(tc)
//  }

  val imageWidth: Float = texture.w
  val imageHeight: Float = texture.h

  def dispose(): Unit = texture.dispose()

}