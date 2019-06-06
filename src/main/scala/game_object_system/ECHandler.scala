package game_object_system
import game_object_system.graphics_objects.SpriteCom

import scala.reflect.{ClassTag, classTag}

object ECHandler {

  var entities : List[Entity] = List()

  var renderableEntities : List[Entity] = List()
  var movableEntities : List[Entity] = List()

  def spawnEntity() : Entity = {
    val e = Entity()
    entities = e :: entities
    e
  }

  def disposeEntities(): Unit = {
    renderableEntities.foreach(e => getThisComponentOfE[SpriteCom](e) match {
      case Some(s) => s.dispose()
      case None =>
    })
  }

  def addComponent(e : Entity, c : Component): Unit = c match  {
    case c : RenderableCom =>
      if (hasThisComponent[PositionCom](e) && hasThisComponent[SpriteCom](e)) {
        e.addComponent(c)
        renderableEntities = e :: renderableEntities
      }
    case c : MovableCom  => if (hasThisComponent[PositionCom](e)){
      e.addComponent(c)
      movableEntities = e :: movableEntities
    }
    case c : InputCom => if (hasThisComponent[MovableCom](e)) e.addComponent(c)
    case _ => e.addComponent(c)
  }

  def hasThisComponent[T : ClassTag](e : Entity) : Boolean =
    getThisComponentOfE[T](e).isDefined

  def entitiesWithThisComponent[T : ClassTag] : List[Entity] =
    entities.filter(getThisComponentOfE[T](_).isDefined)


  /** Input: A parameterized Component type t and an entity e.
    * Output: Option with the first component of type t of e. */
  def getThisComponentOfE[T : ClassTag](e : Entity) : Option[T] =
  e.components match {
    case cs if cs.nonEmpty => getThisComponentFromList[T](cs)
    case _ => None
  }

  /** Input: A parameterized Component type t and a list of compoenents cs.
    * Output: Option with the first component of type t in cs. */
  def getThisComponentFromList[T : ClassTag](cs : List[Component]) : Option[T] =
  cs collectFirst {case a : T if classTag[T].runtimeClass.isInstance(a) => a}

  def getPositionCom(e: Entity): Option[PositionCom] = getThisComponentOfE[PositionCom](e)
  def getMovableCom(e: Entity): Option[MovableCom] = getThisComponentOfE[MovableCom](e)
  def getSpriteCom(e : Entity): Option[SpriteCom] = getThisComponentOfE[SpriteCom](e)
}
