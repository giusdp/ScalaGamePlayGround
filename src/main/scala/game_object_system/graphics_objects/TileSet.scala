package game_object_system.graphics_objects

trait TileSet

case class DungeonTileSet(textureAtlas: TextureAtlas) extends TileSet {

  val floorTiles : Map[Int, Tile] = Map(
    0 -> textureAtlas.makeTile(1),
    1 -> textureAtlas.makeTile(2),
    2 -> textureAtlas.makeTile(3),
    3 -> textureAtlas.makeTile(5),
    4 -> textureAtlas.makeTile(6),
    5 -> textureAtlas.makeTile(7),
    6 -> textureAtlas.makeTile(8),
    7 -> textureAtlas.makeTile(9),
    8 -> textureAtlas.makeTile(21),
    9 -> textureAtlas.makeTile(22),
    10 -> textureAtlas.makeTile(23),
    11 -> textureAtlas.makeTile(25),
    12 -> textureAtlas.makeTile(26),
    13 -> textureAtlas.makeTile(27),
    14 -> textureAtlas.makeTile(28),
    15 -> textureAtlas.makeTile(29),
    16 -> textureAtlas.makeTile(41),
    17 -> textureAtlas.makeTile(42),
    18 -> textureAtlas.makeTile(43),
    10 -> textureAtlas.makeTile(45),
    20 -> textureAtlas.makeTile(46),
    21 -> textureAtlas.makeTile(47),
    22 -> textureAtlas.makeTile(48),
    23 -> textureAtlas.makeTile(49),
  )
  val wallTiles : Map[Int, Tile] = Map()

}

case class Tile ( texCoords: Array[Float])