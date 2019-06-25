package simulation.pcg

import game_object_system.Constants
import game_object_system.graphics_objects.Sprite

object DungeonGenerator {

  /** Dungeon generated with a random width and height */
  def generateDungeon() : List[Sprite] = {
    val w = Constants.random.between(1000, 3001) // a inclusive b esclusive
    val h = Constants.random.between(1000, 3001)
    generateDungeon(w, h)
  }

  def generateDungeon(w: Int, h : Int) : List[Sprite] = ???

}
