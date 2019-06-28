package game_object_system

import com.badlogic.ashley.core.{ComponentMapper, Engine}

object ECEngine{

  val engine : Engine = new Engine()

  val posMapper : ComponentMapper[PositionCom] = ComponentMapper.getFor(PositionCom.getClass)
  val moveMapper : ComponentMapper[VelocityCom] = ComponentMapper.getFor(VelocityCom.getClass)
  val renderableMapper : ComponentMapper[RenderableCom] = ComponentMapper.getFor(RenderableCom.getClass)
  val cameraCenterMapper : ComponentMapper[CameraCenterCom] = ComponentMapper.getFor(CameraCenterCom.getClass)

}
