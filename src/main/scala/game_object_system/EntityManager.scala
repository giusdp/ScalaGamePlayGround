package game_object_system
import scala.reflect.{ClassTag, classTag}

object EntityManager {

  var entityComponentMap : Map[Int, List[Component]] = Map()

  def spawnEntity() : Int = {
    val e = Entity()
    entityComponentMap += e.ID-> List()
    e.ID
  }

  /** Input a map, a key, and a function that transforms the value associated to the key.
    * Output the map with the updated value of that key. */
  def adjust[A, B](m: Map[A, B], k: A)(f: B => B): Map[A, B] = m.updated(k, f(m(k)))

  def addComponent(id : Int, c : Component): Unit =
    entityComponentMap = adjust(entityComponentMap, id)(l => c :: l)

  def getThisComponentOfE[A : ClassTag](id : Int) : Option[A] =
    entityComponentMap.get(id) match {
      case Some(cs) => getComponentFromList[A](cs)
      case None => None
    }


  def getComponentFromList[A : ClassTag](cs : List[Component]) : Option[A] =
    cs collectFirst {case a : A if classTag[A].runtimeClass.isInstance(a) => a}
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
