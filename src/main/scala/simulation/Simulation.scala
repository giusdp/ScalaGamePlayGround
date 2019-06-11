package simulation

import game_object_system.graphics_objects.Camera
import game_object_system.{CameraCenter, ECHandler, Entity, MovableCom, PositionCom}


object Simulation {

  def update(): Unit = {
    updateAllPositions()
  }

  def updateAllPositions(): Unit = ECHandler.movableEntities.foreach(updatePosOfE)

  def updatePosOfE(e: Entity): Unit =
      ECHandler.getMovableCom(e) match {
      case Some(move) =>
        ECHandler.getPositionCom(e) match {
            case Some(pos) =>
              updatePosition(pos, move)
              if (ECHandler.hasThisComponent[CameraCenter](e)){
                Camera.updateCamera(pos.x, pos.y)
              }
            case None =>
          }
      case None =>
    }


  def updatePosition(p : PositionCom, m: MovableCom) : Unit = {
    p.addToX(m.state_machine.direction._1 * m.velX)
    p.addToY(m.state_machine.direction._2 * m.velY)

    p.model_matrix.identity().translate(p.x, p.y, 0)
  }

}
