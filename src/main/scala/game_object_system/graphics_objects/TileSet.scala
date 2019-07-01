package game_object_system.graphics_objects

import com.badlogic.ashley.core.Entity

trait TileMap extends Entity

case class DungeonTileMap(textureAtlas: TextureAtlas) extends TileMap {

  val tiles : Map[String, Tile] = Map(
    // FLOOR
    1 -> textureAtlas.makeTile(1),
    2 -> textureAtlas.makeTile(2),
    3 -> textureAtlas.makeTile(3),
    4 -> textureAtlas.makeTile(5),
    5 -> textureAtlas.makeTile(6),
    6 -> textureAtlas.makeTile(7),
    7 -> textureAtlas.makeTile(8),
    8 -> textureAtlas.makeTile(9),
    9 -> textureAtlas.makeTile(21),
    10 -> textureAtlas.makeTile(22),
    11 -> textureAtlas.makeTile(23),
    12 -> textureAtlas.makeTile(25),
    13 -> textureAtlas.makeTile(26),
    14 -> textureAtlas.makeTile(27),
    15 -> textureAtlas.makeTile(28),
    16 -> textureAtlas.makeTile(29),
    17 -> textureAtlas.makeTile(41),
    18 -> textureAtlas.makeTile(42),
    19 -> textureAtlas.makeTile(43),
    20 -> textureAtlas.makeTile(45),
    21 -> textureAtlas.makeTile(46),
    22 -> textureAtlas.makeTile(47),
    23 -> textureAtlas.makeTile(48),
    24 -> textureAtlas.makeTile(49),

    // WALLS
  )

}