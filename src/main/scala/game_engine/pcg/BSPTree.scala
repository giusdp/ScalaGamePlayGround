package game_engine.pcg

import DungeonGenerator.random
import DungeonGenerator.minRoom

sealed trait BSPTree
case class BSPNode(x : Int, y : Int, w: Int, h : Int, left : BSPTree, right: BSPTree) extends BSPTree
case class BSPLeaf(x : Int, y : Int, w: Int, h : Int) extends BSPTree

object BSPTree {

  def buildBSPTree(limit : Int, width : Int, height : Int) : BSPTree = {
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

  private def split(r : Int) = random.between(minRoom, r-minRoom)
}