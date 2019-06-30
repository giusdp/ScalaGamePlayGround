package game_engine.pcg

import com.badlogic.ashley.core.Entity


object DungeonGenerator {

  /** Dungeon generated with a random width and height */
  def generateDungeon() : List[Entity] = {
    val w = 3 // a inclusive b esclusive
    val h = 3
    generateDungeon(w, h)
  }

  def generateDungeon(w: Int, h : Int) : List[Entity] = ???

}
