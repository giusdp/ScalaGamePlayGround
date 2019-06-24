package simulation

import game_object_system.graphics_objects.Camera
import game_object_system.{CameraCenter, ECHandler, Entity, MovableCom, PositionCom, RenderableCom}

object Simulation {

  def update(): Unit = {
    updateAllPositions()
  }

  def updateAllPositions(): Unit = ECHandler.movableEntities.foreach(updatePosOfE)

  def updatePosOfE(e: Entity): Unit = ECHandler.getMovableCom(e) match {
    case Some(move) => ECHandler.getPositionCom(e) match {
      case Some(pos) => updatePosition(pos, move,
        ECHandler.getRenderableCom(e), ECHandler.hasThisComponent[CameraCenter](e))
      case None =>
    }
    case None =>
  }


  def updatePosition(p : PositionCom, m: MovableCom, r : Option[RenderableCom], cc : Boolean) : Unit = {
    p.addToX(m.state_machine.direction._1 * m.velX)
    p.addToY(m.state_machine.direction._2 * m.velY)
    r match {
      case Some(rc) =>
        rc.sprite.getModelMatrix.identity().translate(p.x, p.y, 0)
        println(rc.sprite.getModelMatrix)
        if (cc) Camera.setPosition(p.x, p.y, 0) else {}
      case None =>
    }
  }

}
