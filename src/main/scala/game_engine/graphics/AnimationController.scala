package game_engine.graphics

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import game_object_system.{AnimationCom, RenderableCom}

class AnimationController(priority : Int) extends IteratingSystem(
  Family.all(classOf[RenderableCom], classOf[AnimationCom]).get(), priority){
  override def processEntity(entity: Entity, deltaTime: Float): Unit = ???
}
