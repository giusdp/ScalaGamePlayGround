package game_object_system
import scala.reflect.{ClassTag, classTag}

object ECHandler {

  var entities : List[Entity] = List()

//  var entityComponentMap : Map[Int, List[Component]] = Map()

  def spawnEntity() : Entity = {
    val e = Entity()
    entities = e :: entities
    e
  }

  def addComponent(e : Entity, c : Component): Unit = e.addComponent(c)

  def entitiesWithThisComponent[T : ClassTag] : List[Entity] =
    entities.filter(getThisComponentOfE[T](_).isDefined)

  def getPositionCom(e: Entity): Option[PositionCom] = getThisComponentOfE[PositionCom](e)
  def getMovableCom(e: Entity): Option[MovableCom] = getThisComponentOfE[MovableCom](e)

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


}
