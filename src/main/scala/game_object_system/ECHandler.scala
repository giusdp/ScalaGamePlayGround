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

//  /** Input: a map, a key, and a function that transforms the value associated to the key.
//    * Output: the map with the transformed value of that key. */
//  def adjust[A, B](m: Map[A, B], k: A)(f: B => B): Map[A, B] = m.updated(k, f(m(k)))
//
//  /** Input: id of an entity id and a component c.
//    * Side Effect: Adds c to the list of components of id (doesn't check if c is already a component of id)
//    * Output: unit */
//  def addComponent(id : Int, c : Component): Unit =
//    entityComponentMap = adjust(entityComponentMap, id)(c :: _)
//
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
