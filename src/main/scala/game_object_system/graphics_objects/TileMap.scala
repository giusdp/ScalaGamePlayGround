package game_object_system.graphics_objects

import java.awt.Rectangle

case class TileLayer(name: String, width: Int, height: Int, tiles: Array[Model])

case class ObjectLayer(name:String, width:Int, height:Int, objects: List[ObjectShape])
case class ObjectShape(gid:Int, rect:Rectangle)

case class TileMap(
                    width : Int,
                    height : Int,
                    tileWidth: Int,
                    tileHeight: Int,
                    tileSet: TextureAtlas,
                    tileLayers: Seq[TileLayer],
                    objectGroup: Seq[ObjectLayer]
                  )
//  var mapLayers : Seq[Model] = _



//object TileMap {
//  def apply(width: Int, height: Int, tileWidth: Int, tileHeight: Int, tileSet: TextureAtlas, tileLayers: Seq[TileLayer],
//            objectGroup: Seq[ObjectLayer]): TileMap = {
//
//    val map = new TileMap(width, height, tileWidth, tileHeight, tileSet, tileLayers, objectGroup)
//
//    var indices : List[Array[Int]] = List()
//    var vertices : List[Array[Float]] = List()
//    var texCoords : List[Array[Float]] = List()
//
//    map.mapLayers = tileLayers.map(l => {
//      val inds : Array[Int] = prepareIndices(l.tiles)
//      for (y <- 0 until l.height) for (x <- 0 until l.width){
//        if (l.tiles(y * l.height + x).gid != 0) {
//          val vs : Array[Float] = Array(
//            x*tileWidth-(tileWidth/2), y*tileHeight+(tileHeight/2), 0,
//            x*tileWidth-(tileWidth/2), y*tileHeight-(tileHeight/2), 0,
//            x*tileWidth+(tileWidth/2), y*tileHeight-(tileHeight/2), 0,
//            x*tileWidth+(tileWidth/2), y*tileHeight+(tileHeight/2), 0
//          )
//          val tcs = tileSet.extractRegion(l.tiles(y * l.height + x).gid)
//          indices = inds :: indices
//          vertices = vs :: vertices
//          texCoords = tcs :: texCoords
//        }
//      }
//      ModelLoader.loadModel(indices.flatten.toArray, vertices.flatten.toArray, texCoords.flatten.toArray)
//    })
//
//    map
//  }
//
//  def prepareIndices(tiles : Array[Tile]) : Array[Int] = {
//    val baseInds: Array[Int] = Array(0,1,3, 3,1,2)
//    tiles.filterNot(_.gid==0).indices.flatMap(i => baseInds.map(_+(i*4))).toArray
//  }
//}
