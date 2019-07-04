package game_object_system.graphics_objects

case class TextureAtlas(texture : Texture, cellSize : Float, sheetWidth : Float, sheetHeight: Float) {

  def makeTile(index : Int) : Tile = {
    val tw : Float = cellSize/sheetWidth
    val th = cellSize/sheetHeight
    val rows = sheetWidth/cellSize
    val tx = (index % rows) / tw
    val ty = (index / rows + 1) * th
    val tc = Array(
      tx, ty,
      tx + tw, ty,
      tx + tw, ty + th,
      tx, ty + th
    )
    Tile(tc)
  }

  def dispose(): Unit = texture.dispose()

}