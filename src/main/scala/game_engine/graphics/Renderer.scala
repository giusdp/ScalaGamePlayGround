package game_engine.graphics

import game_object_system.{ECHandler, Entity, PositionCom, GraphicsCom}

object Renderer {

  def renderFrame() = {
//    ECHandler.renderableEntities.foreach(e => ECHandler.getThisComponentOfE[RenderableCom])
  }

  def renderEntity(e: Entity) = {
    //var r = ECHandler.getThisComponentOfE[RenderableCom](e)
    ECHandler.getThisComponentOfE[PositionCom](e) match {
      case Some(pos) =>
      case None =>
    }

  }

}
