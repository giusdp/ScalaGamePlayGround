package simulation

import game_object_system._

object Simulation {

  def update(): Unit = {
    updatePositions()
    updateRenderables()
  }

  private def updatePositions(): Unit = ECHandler.entitiesWithPosition.foreach(updatePosOfE)

  private def updateRenderables() : Unit = ECHandler.renderableEntities.foreach(updateModel)

  private def updateModel(e : Entity): Unit = (ECHandler.getRenderableCom(e), ECHandler.getPositionCom(e)) match {
    case (Some(r), Some(p)) => r.sprite.getModelMatrix.identity().translate (p.x, p.y, 0)
    case _ =>
  }

  private def updatePosOfE(e: Entity): Unit = (ECHandler.getPositionCom(e), ECHandler.getMovableCom(e)) match {
    case (Some(pos), Some(move)) => updatePosition(pos, move, ECHandler.hasThisComponent[CameraCenter](e))
    case _ =>
  }

  private def updatePosition(p : PositionCom, m: MovementCom, cc : Boolean) : Unit = {
    p.addToX(m.state_machine.direction._1 * m.velX)
    p.addToY(m.state_machine.direction._2 * m.velY)
  }

}
