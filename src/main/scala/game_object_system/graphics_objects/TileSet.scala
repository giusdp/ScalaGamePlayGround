package game_object_system.graphics_objects

trait TileSet

case class DungeonTileSet(textureAtlas: TextureAtlas) extends TileSet {

  val floorTiles : Map[Int, Tile] = Map(
    0 -> textureAtlas.makeTile(0),
    1 -> textureAtlas.makeTile(1),
    2 -> textureAtlas.makeTile(2),
    3 -> textureAtlas.makeTile(4),
    4 -> textureAtlas.makeTile(5),
    5 -> textureAtlas.makeTile(6),
    6 -> textureAtlas.makeTile(7),
    7 -> textureAtlas.makeTile(8),
    8 -> textureAtlas.makeTile(20),
    9 -> textureAtlas.makeTile(21),
    10 -> textureAtlas.makeTile(22),
    11 -> textureAtlas.makeTile(24),
    12 -> textureAtlas.makeTile(25),
    13 -> textureAtlas.makeTile(26),
    14 -> textureAtlas.makeTile(27),
    15 -> textureAtlas.makeTile(28),
    16 -> textureAtlas.makeTile(40),
    17 -> textureAtlas.makeTile(41),
    18 -> textureAtlas.makeTile(42),
    19 -> textureAtlas.makeTile(44),
    20 -> textureAtlas.makeTile(45),
    21 -> textureAtlas.makeTile(46),
    22 -> textureAtlas.makeTile(47),
    23 -> textureAtlas.makeTile(48),
  )
  val wallTiles : Map[Int, Tile] = Map()

}

case class Tile ( texCoords: Array[Float])