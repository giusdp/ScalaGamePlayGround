package simulation.pcg

import game_object_system.Globals
import javax.swing.text.html.parser.Entity

object DungeonGenerator {

  /** Dungeon generated with a random width and height */
  def generateDungeon() : List[Entity] = {
    val w = Globals.random.between(300, 1001) // a inclusive b esclusive
    val h = Globals.random.between(300, 1001)
    generateDungeon(w, h)
  }

  def generateDungeon(w: Int, h : Int) : List[Entity] = ???

}
