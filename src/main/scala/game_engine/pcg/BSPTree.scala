package game_engine.pcg

import game_object_system.graphics_objects.Rect

import scala.util.Random

sealed trait BSPTree
case class BSPNode(x : Int, y : Int, w: Int, h : Int, left : BSPTree, right: BSPTree) extends BSPTree
case class BSPLeaf(x : Int, y : Int, w: Int, h : Int) extends BSPTree

object BSPBuilder {

  val random: Random = Random
  val tileSize = 16
  val minRoom: Int = 3 * tileSize


  def buildDungeon(limit : Int, width : Int, height : Int) : BSPTree = {
    def buildBSP(l: Int, x: Int, y: Int, w: Int, h: Int): BSPTree = {
      if (l >= limit) BSPLeaf(x, y, w, h)
      else {
        val c = l+1
        // 1. choose randomly either a vertical or horizontal split
        random.between(0, 2) match {
          // 2. choose randomly a point of split checking the borders
          // 3. split by creating 2 different nodes one for each space where the split occurred
          case 0 =>
            if (w > 2 * minRoom) {
              val sp = split(w)
              val left = buildBSP(c, x, y, sp, h)
              val right = buildBSP(c, x + sp + 1, y, w - sp, h)
              BSPNode(x, y, w, h, left, right)
            }
            else BSPLeaf(x, y, w, h)
          case 1 =>
            if (h > 2 * minRoom) {
              val sp = split(h)
              val left = buildBSP(c, x, y, w, sp)
              val right = buildBSP(c, x, y + sp + 1, w, h - sp)
              BSPNode(x, y, w, h, left, right)
            }
            else BSPLeaf(x, y, w, h)
        }
      }
    }

    buildBSP(0, 0, 0, width, height)
  }
  def createRooms(t : BSPTree) : List[Rect] = t match {
    case BSPNode(_, _, _, _, l, r) => createRooms(l) ++ createRooms(r)
    case BSPLeaf(x,y,w,h) =>
      val rw = Math.abs(random.nextInt()) % (w-minRoom+1)
      val rh = Math.abs(random.nextInt()) % (h-minRoom+1)
      List(Rect(x+rw,y+rh,w,h))
  }
  private def split(r : Int) = random.between(minRoom, r-minRoom)
}