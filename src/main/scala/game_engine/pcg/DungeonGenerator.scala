package game_engine.pcg

import datamanager.ModelLoader
import game_object_system.graphics_objects.{DungeonTileSet, Model}

import scala.util.Random

object DungeonGenerator {
  case class Rect(x : Float, y : Float, w: Float, h : Float)

  val tileSize : Float = 16
  val minRoom: Int = 4 * tileSize.toInt
  val random: Random = Random

  /** Dungeon generated with a random width and height (between 200 and 800)*/
  def generateDungeon(ts : DungeonTileSet): Model = {
    val w = random.between(200, 801) // a inclusive b esclusive
    val h = random.between(200, 801)
    generateDungeon(w, h, ts)
  }

  def generateDungeon(w: Int, h : Int, ts : DungeonTileSet): Model = {
    val tree = BSPTree.buildBSPTree(3, w, h) // With a limit of 3, the rooms generated are always 7/8
    var rooms = removeRandomRooms(roomsRects(tree), random.nextInt(4))
    prepareDungeonModel(rooms, ts)
  }

  private def roomsRects(t : BSPTree) : List[Rect] = t match {
    case BSPNode(_, _, _, _, l, r) => roomsRects(l) ++ roomsRects(r)
    case BSPLeaf(x,y,w,h) =>
      val rw = Math.abs(random.nextInt()) % (w-minRoom+1)
      val rh = Math.abs(random.nextInt()) % (h-minRoom+1)
      List(Rect(x+rw,y+rh,w,h))


  }

  private def removeRandomRooms(rooms : List[Rect], maxToRemove : Int): List[Rect] = {
    var rs = rooms
    if (rooms.length > 5) // just to make sure
    { // remove first 0, 1, 2 or 3 rooms TODO: check if alright results
      val roomsToRemove = (0 to random.between(0, maxToRemove+1)).map(_ => rooms(random.nextInt(rooms.length)))
      rs = rooms.filter(!roomsToRemove.contains(_))
    }
    rs
  }

  private def prepareDungeonModel(rooms : List[Rect], ts : DungeonTileSet): Model = {

    val baseInds: Array[Int] = Array(
      0,1,3,
      3,1,2
    )

    val indices: Array[Int] = rooms.flatMap(_ => baseInds.map(_+4)).toArray

    val vertices: Array[Float] = rooms.flatMap(processRoom).toArray

    val texCoords: Array[Float] = (for (_ <- 0 until vertices.length/4) yield {
      ts.floorTiles(random.nextInt(ts.floorTiles.size)).texCoords
    }).flatten.toArray

    ModelLoader.loadModel(indices, vertices, texCoords)
  }

  private def processRoom(room: Rect): Array[Float] = {
    val numCols : Int = ((room.w - room.x) / tileSize).toInt
    val numRows : Int = ((room.h - room.y) / tileSize).toInt
    var list : List[Array[Float]] = List()
    for (x <- 0 to numCols) for (y <- 0 to numRows){
      val posX = room.x + (x * tileSize + 1)
      val posY = room.y + (y * tileSize)
      val vertices = Array(
        posX, posY + (tileSize/2.0f), 0f,
        posX - (tileSize/2.0f) , posY - (tileSize/2.0f), 0f,
        posX + (tileSize/2.0f), posY - (tileSize/2.0f), 0f,
        posX + (tileSize/2.0f), posY + (tileSize/2.0f), 0f,
      )
      list = vertices :: list
    }

    list.flatten.toArray
  }

}
