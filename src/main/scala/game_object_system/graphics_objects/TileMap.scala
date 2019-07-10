package game_object_system.graphics_objects

import java.awt.Rectangle

case class TileSet(indexedTiles : Map[Int, Array[Float]], textureAtlas: TextureAtlas) {
  def bindTextureAtlas(unit : Int): Unit = textureAtlas.texture.bind(unit)
}

case class Tile(x : Int, y : Int,texCoords : Array[Float])
case class TileLayer(name: String, width: Int, height: Int, tmModel : Model, nTiles : Int)

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

//  val pointsArrayModel: Model = buildPoints()
//  private def buildPoints(): Model = {
//    val tilePoints: Seq[Float] = tileLayers.zipWithIndex.flatMap(l=> l._1.tiles.map(tile => Array(tile.x.toFloat, tile.y, l._2))).flatten
//    ModelLoader.loadVerticesOnlyModel(tilePoints.toArray)
//  }
  def scaleMap(scaleFactor : Float): Unit = tileLayers.foreach(_.tmModel.scale(scaleFactor)) // not working?


  def translateMap(x: Float, y: Float, z : Float) : Unit = tileLayers.foreach(_.tmModel.translate(x,y,z))
}
