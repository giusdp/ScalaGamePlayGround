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
    val v = ECEngine.velMapper.get(entity).velocity
    val m = ECEngine.msmMpper.get(entity)
    p.addToX(deltaTime * m.msm.direction._1 * v)
    p.addToY(deltaTime * m.msm.direction._2 * v)

    if (ECEngine.renderableMapper.has(entity)) {
      ECEngine.renderableMapper.get(entity).sprite.moveSprite(p.x, p.y, p.z)
    }
  }
}
