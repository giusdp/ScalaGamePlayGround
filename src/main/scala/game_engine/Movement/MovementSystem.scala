package simulation.Movement

import com.badlogic.ashley.core.{Engine, EntitySystem}

class MovementSystem extends EntitySystem {
  override def addedToEngine(engine: Engine): Unit = super.addedToEngine(engine)

  override def removedFromEngine(engine: Engine): Unit = super.removedFromEngine(engine)

  override def update(deltaTime: Float): Unit = super.update(deltaTime)
}
