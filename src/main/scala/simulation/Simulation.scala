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
        if (! move.state_machine.isStill) {
          val p = ECHandler.getPositionCom(e)
          p match {
            case Some(pos) =>
              pos.addToX(move.state_machine.currentDirection._1 * move.velX)
              pos.addToY(move.state_machine.currentDirection._2 * move.velY)
              pos.model_matrix.translate(pos.x, pos.y, 0)
            case None =>
          }
        }
    else{

        }

      case None =>
    }
  }

}
