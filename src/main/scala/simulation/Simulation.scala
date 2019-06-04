package simulation

import game_object_system.{ECHandler, Entity, MovableCom}


object Simulation {

  def update(): Unit = {
    ECHandler.movableEntities.foreach(updatePosOfE)

  }

  def updatePosOfE(e: Entity): Unit = {
    val m = ECHandler.getMovableCom(e)
    m match {


      case Some(move) =>
        val p = ECHandler.getPositionCom(e)
        p match {
          case Some(pos) => move.state_machine.updatePos(pos, (move.velX, move.velY))
          case None =>
        }

      case None =>
    }
  }

}
