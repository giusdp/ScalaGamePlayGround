package simulation

import game_object_system.{ECHandler, Entity, MovableCom}


object Simulation {

  def update(): Unit = {
    ECHandler.entitiesWithThisComponent[MovableCom].foreach(updatePosOfE)

  }

  def updatePosOfE(e: Entity): Unit = {
    val m = ECHandler.getMovableCom(e)
    m match {


      case Some(move) =>
        val p = ECHandler.getPositionCom(e)
        p match {
          case Some(pos) => move.movePos(pos)
          case None =>
        }

      case None =>
    }
  }

}
