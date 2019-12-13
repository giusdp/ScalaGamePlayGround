package game_engine.graphics

import com.badlogic.ashley.core.{Entity, Family}
import com.badlogic.ashley.systems.IteratingSystem
import game_object_system.graphics_objects.AnimatedSprite
import game_object_system.{AnimationCom, ECEngine, RenderableCom}

class AnimationController(priority : Int) extends IteratingSystem(
  Family.all(classOf[RenderableCom], classOf[AnimationCom]).get(), priority){

  var elapsetTime : Float = 0

  override def processEntity(entity: Entity, deltaTime: Float): Unit = {

    val sprite = ECEngine.renderableMapper.get(entity).sprite.asInstanceOf[AnimatedSprite]

    elapsetTime += deltaTime
    if (elapsetTime >= sprite.fps) {
      elapsetTime -= sprite.fps
      sprite.nextFrame()
    }
  }
}
