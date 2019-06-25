package simulation.pcg

import game_object_system.Constants.{random, tileSize}

import scala.annotation.tailrec

sealed trait BSPTree
case class BSPNode(x : Int, y : Int, w: Int, h : Int, left : BSPTree, right: BSPTree) extends BSPTree
case class BSPLeaf(x : Int, y : Int, w: Int, h : Int) extends BSPTree

object BSPBuilder {

  val minRoom: Int = 5 * tileSize

  def buildBSP(x : Int, y : Int, w : Int, h : Int): BSPTree = {
    // 1. choose randomly either a vertical or horizontal split
    random.between(0, 2) match {
      // 2. choose randomly a point of split checking the borders
      // 3. split by creating 2 different nodes one for each space where the split occurred
      case 0 =>
        if (w > 2*minRoom) {
          val sp = split(w)
          val left = buildBSP(x, y, sp, h)
          val right = buildBSP(x+sp+1, y, w-sp, h)
          BSPNode(x, y, w, h, left, right)
        }
        else BSPLeaf(x,y,w,h)
      case 1 =>
        if (h > 2*minRoom) {
          val sp = split(h)
          val left = buildBSP(x, y, w, sp)
          val right = buildBSP(x, y+sp+1, w, h-sp)
          BSPNode(x,y,w, h, left, right)
        }
        else BSPLeaf(x,y,w,h)
    }
  }

  def getCells(tree : BSPTree, l : List[(Int, Int, Int, Int)]) : List[(Int,Int,Int, Int)]= tree match {
    case BSPLeaf(x,y,w, h) => (x,y,w,h) :: l
    case BSPNode(_,_,_,_, left, right) => getCells(left, l) ++ getCells(right, l)
  }
  
  private def split(r : Int) = random.between(minRoom, r-minRoom)
}