package game_object_system

object SpawnedEntities {

  var entities : List[Entity] = List()

  def filterEntitiesByComponent[A <: Component]: List[Entity] =
    entities.filter(_.components.exists(_.isInstanceOf[A]))

  def getComponent[A <: Component](c : List[Component]) : A =
    c.filter(_.isInstanceOf[A]).head.asInstanceOf[A]

  def getAllOfThisComponents[A <: Component]: List[A] =
    filterEntitiesByComponent[A].map(e => getComponent[A](e.components))

  def getFirstOfThisComponent[A <: Component]: Option[A] =
   getAllOfThisComponents[A] match {
    case x :: _ => Some(x)
    case _ => None
  }

}
