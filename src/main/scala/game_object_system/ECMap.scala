package game_object_system
import scala.reflect.{ClassTag, classTag}

object ECMap {

  var entityComponentMap : Map[Int, List[Component]] = Map()

  def spawnEntity() : Int = {
    val e = Entity.spawnEntity()
    entityComponentMap += e -> List()
    e
  }

  /** Input: a map, a key, and a function that transforms the value associated to the key.
    * Output: the map with the transformed value of that key. */
  def adjust[A, B](m: Map[A, B], k: A)(f: B => B): Map[A, B] = m.updated(k, f(m(k)))

  /** Input: id of an entity id and a component c.
    * Side Effect: Adds c to the list of components of id (doesn't check if c is already a component of id)
    * Output: unit */
  def addComponent(id : Int, c : Component): Unit =
    entityComponentMap = adjust(entityComponentMap, id)(c :: _)

  /** Input: A parameterized Component type t and an entity id.
    * Output: Option with the first component of type t of id. */
  def getThisComponentOfE[T : ClassTag](id : Int) : Option[T] =
    entityComponentMap.get(id) match {
      case Some(cs) => getThisComponentFromList[T](cs)
      case None => None
    }

  /** Input: A parameterized Component type t and a list of compoenents cs.
    * Output: Option with the first component of type t in cs. */
  def getThisComponentFromList[T : ClassTag](cs : List[Component]) : Option[T] =
    cs collectFirst {case a : T if classTag[T].runtimeClass.isInstance(a) => a}
//
//  def getAllOfThisComponents[A]: List[A] =
//    entityComponentMap.values.flatten.filter(_.isInstanceOf[A]).map(_.asInstanceOf[A]).toList
  //  var entities : List[Entity] = List()
  //
  //  def filterEntitiesByComponent[A <: Component]: List[Entity] =
  //    entities.filter(_.components.exists(_.isInstanceOf[A]))
  //
//
//  def getAllOfThisComponents[A <: Component]: List[A] =
//    filterEntitiesByComponent[A].map(e => getComponent[A](e.components))
//
//  def getFirstOfThisComponent[A <: Component]: Option[A] =
//   getAllOfThisComponents[A] match {
//    case x :: _ => Some(x)
//    case _ => None
//  }

}
