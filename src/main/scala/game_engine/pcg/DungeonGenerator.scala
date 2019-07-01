package game_engine.pcg

import com.badlogic.ashley.core.Entity

import scala.util.Random

object DungeonGenerator {
  case class Rect(x : Float, y : Float, w: Float, h : Float)

  val tileSize = 16
  val minRoom: Int = 4 * tileSize
  val random: Random = Random

  /** Dungeon generated with a random width and height */
  def generateDungeon() = {
    val w = random.between(200, 801) // a inclusive b esclusive
    val h = random.between(200, 801)
    generateDungeon(w, h)
  }

  def generateDungeon(w: Int, h : Int)  = {
    val tree = BSPTree.buildBSPTree(3, w, h) // With a limit of 3, the rooms generated are always 7/8
    var rooms = removeRandomRooms(roomsRects(tree), random.nextInt(4))
  }

  def roomsRects(t : BSPTree) : List[Rect] = t match {
    case BSPNode(_, _, _, _, l, r) => roomsRects(l) ++ roomsRects(r)
    case BSPLeaf(x,y,w,h) =>
      val rw = Math.abs(random.nextInt()) % (w-minRoom+1)
      val rh = Math.abs(random.nextInt()) % (h-minRoom+1)
      List(Rect(x+rw,y+rh,w,h))


  }

  def removeRandomRooms(rooms : List[Rect], maxToRemove : Int): List[Rect] = {
    var rs = rooms
    if (rooms.length > 5) // just to make sure
    { // remove first 0, 1, 2 or 3 rooms TODO: check if alright results
      val roomsToRemove = (0 to random.nextInt(maxToRemove)).map(_ => rooms(random.nextInt(rooms.length)))
      rs = rooms.filter(!roomsToRemove.contains(_))
    }
    rs
  }

}
