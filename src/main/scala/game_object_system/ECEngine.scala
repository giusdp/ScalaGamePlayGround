package game_object_system

import com.badlogic.ashley.core.{ComponentMapper, Engine}

object ECEngine{

  val engine : Engine = new Engine()

  val posMapper : ComponentMapper[PositionCom] = ComponentMapper.getFor(classOf[PositionCom])
  val velMapper : ComponentMapper[VelocityCom] = ComponentMapper.getFor(classOf[VelocityCom])
  val dirMapper : ComponentMapper[DirectionCom] = ComponentMapper.getFor(classOf[DirectionCom])
  val renderableMapper : ComponentMapper[RenderableCom] = ComponentMapper.getFor(classOf[RenderableCom])
  val cameraCenterMapper : ComponentMapper[CameraCenterCom] = ComponentMapper.getFor(classOf[CameraCenterCom])
  val tileMapMapper : ComponentMapper[TileMapCom] = ComponentMapper.getFor(classOf[TileMapCom])
  val animMapper : ComponentMapper[AnimationCom] = ComponentMapper.getFor(classOf[AnimationCom])

}
