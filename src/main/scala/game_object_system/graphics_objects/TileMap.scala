package game_object_system.graphics_objects

import java.awt.Rectangle

case class TileSet(indexedTiles : Map[Int, Array[Float]], textureAtlas: TextureAtlas) {
  def bindTextureAtlas(unit : Int): Unit = textureAtlas.bind(unit)
  def getRegionWidth: Float = textureAtlas.regionWidth
  def getRegionHeight: Float = textureAtlas.regionHeight
}

case class Tile(x : Int, y : Int,texCoords : Array[Float])
case class TileLayer(width: Int, height: Int, tmModel : Model, nTiles : Int)

case class ObjectShape(gid:Int, rect:Rectangle)
case class ObjectLayer(name:String, width:Int, height:Int, objects: List[ObjectShape])

case class TileMap(
                    width : Int, // number of cols of tiles
                    height : Int, // number of row of tiles
                    tileWidth: Int, // number of pixels of a tile
                    tileHeight: Int,
                    tileSet: TileSet,
                    tileLayers: Seq[TileLayer],
                    objectGroup: Seq[ObjectLayer]
                  ) {

  def scaleMap(scaleFactor : Float): Unit = tileLayers.foreach(_.tmModel.scale(scaleFactor)) // not working?

  def translateMap(x: Float, y: Float, z : Float) : Unit = tileLayers.foreach(_.tmModel.translate(x,y,z))
}
