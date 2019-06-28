package game_engine.Movement

import game_object_system.{ECEngine, VelocityCom, PositionCom}
import com.badlogic.ashley.core.{Engine, Entity, EntitySystem, Family}
import scala.jdk.CollectionConverters._

class MovementSystem extends EntitySystem {

  var entities: Iterable[Entity] = _

  override def addedToEngine(engine: Engine): Unit = {
    entities = ECEngine.engine.getEntitiesFor(Family.all(PositionCom.getClass, VelocityCom.getClass).get()).asScala
  }

  override def update(deltaTime: Float): Unit = {
    entities.foreach(e => {
      val p = ECEngine.posMapper.get(e)
      val m = ECEngine.moveMapper.get(e)
      p.addToX(deltaTime * m.velX)
      p.addToY(deltaTime * m.velY)
    })
  }

}
