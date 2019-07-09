package game_object_system.graphics_objects

import java.awt.Rectangle

case class TileSet(indexedTiles : Map[Int, Tile], textureAtlas: TextureAtlas) {
  def bindTextureAtlas(unit : Int): Unit = textureAtlas.texture.bind(unit)
}

case class Tile(texCoords : Array[Float])
case class TileLayer(name: String, width: Int, height: Int, tiles: Array[Model])

case class ObjectShape(gid:Int, rect:Rectangle)
case class ObjectLayer(name:String, width:Int, height:Int, objects: List[ObjectShape])

case class TileMap(
                    width : Int,
                    height : Int,
                    tileWidth: Int,
                    tileHeight: Int,
                    tileSet: TileSet,
                    tileLayers: Seq[TileLayer],
                    objectGroup: Seq[ObjectLayer]
                  ) {
  def scaleMap(x: Float, y: Float, z : Float): Unit = tileLayers.foreach(_.tiles.foreach(_.scale(x, y, z))) // not working?


  def translateMap(x: Float, y: Float, z : Float) : Unit = tileLayers.foreach(_.tiles.foreach(_.translate(x,y,z)))
}
