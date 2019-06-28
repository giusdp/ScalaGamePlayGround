package game_object_system

import com.badlogic.ashley.core.{ComponentMapper, Engine}

object ECEngine{

  val engine : Engine = new Engine()

  val posMapper : ComponentMapper[PositionCom] = ComponentMapper.getFor(classOf[PositionCom])
  val moveMapper : ComponentMapper[VelocityCom] = ComponentMapper.getFor(classOf[VelocityCom])
  val renderableMapper : ComponentMapper[RenderableCom] = ComponentMapper.getFor(classOf[RenderableCom])
  val cameraCenterMapper : ComponentMapper[CameraCenterCom] = ComponentMapper.getFor(classOf[CameraCenterCom])

}
