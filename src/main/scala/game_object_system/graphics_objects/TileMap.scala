package game_object_system

import java.awt.Rectangle

import game_object_system.graphics_objects.TextureAtlas

case class TMXTile(gid : Int)

case class TMXLayer(name: String, width: Int, height: Int, tiles: Array[TMXTile])
object TMXLayer{
  def apply(name: String, width: Int, height: Int, data: String): TMXLayer = {
    val tiles: Array[TMXTile] = data.split(",").map(t => TMXTile(t.trim.toInt))
    new TMXLayer(name, width, height, tiles)
  }
}

case class TMXObject(gid:Int, rect:Rectangle)
case class TMXObjectGroup(name:String, width:Int, height:Int, objects: List[TMXObject])

case class TMXTileSet(name:String, tileWidth:Int, tileHeight:Int, tsImage: TextureAtlas)


case class TMXMap(
                   width : Int,
                   height : Int,
                   tileWidth: Int,
                   tileHeight: Int,
                   tileSet: TMXTileSet,
                   layers: Seq[TMXLayer],
                   objectGroup: Seq[TMXObjectGroup]
                 )
