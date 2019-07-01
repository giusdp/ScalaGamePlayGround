package game_object_system.graphics_objects

import datamanager.ModelLoader

case class SpriteSheet(texture : Texture, cellSize : Float, sheetWidth : Float, sheetHeight: Float) {

  def singleSprite(index : Int, x : Float, y : Float) = {
    val tw : Float = cellSize/sheetWidth
    val th = cellSize/sheetHeight
    val rows = sheetWidth/cellSize
    val tx = (index % rows) / tw
    val ty = (index / rows + 1) * th

    val texCoords = Array(
      tx, ty,
      tx + tw, ty,
      tx + tw, ty + th,
      tx, ty + th
    )

    val r = CustomTextureRect(x, y, cellSize, cellSize, texCoords)
    ModelLoader.loadModel(r)

  }
}