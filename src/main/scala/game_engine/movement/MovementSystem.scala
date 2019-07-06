package game_engine.movement

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import game_object_system.graphics_objects.Camera
import game_object_system.{DirectionCom, ECEngine, PositionCom, VelocityCom}
import org.joml.Vector3f

class MovementSystem(priority : Int) extends IteratingSystem(
  Family.all(classOf[PositionCom], classOf[VelocityCom], classOf[DirectionCom]).get(),
  priority
)
{

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {
    val p = ECEngine.posMapper.get(entity)
    val v = ECEngine.velMapper.get(entity).velocity
    val (dx, dy) = ECEngine.dirMapper.get(entity).dir
    p.addToX(deltaTime * dx * v)
    p.addToY(deltaTime * dy * v)

    if (ECEngine.renderableMapper.has(entity)) {
      ECEngine.renderableMapper.get(entity).sprite.moveSprite(p.x, p.y, p.z)
    }
    if (ECEngine.cameraCenterMapper.has(entity)) {
      Camera.setPosition(p.x, p.y)
    }
  }
}