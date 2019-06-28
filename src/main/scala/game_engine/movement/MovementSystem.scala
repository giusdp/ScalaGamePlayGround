package game_engine.movement

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import game_object_system.{ECEngine, PositionCom, VelocityCom}

class MovementSystem(priority : Int) extends IteratingSystem(
  Family.all(classOf[PositionCom], classOf[VelocityCom]).get(),
  priority
) {


  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
      val p = ECEngine.posMapper.get(entity)
      val m = ECEngine.moveMapper.get(entity)
      p.setX(deltaTime * m.velX)
      p.setY(deltaTime * m.velY)
  }
}
