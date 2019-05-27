package state_system

object SpawnedEntities {

  var entities : List[Entity] = List(Entity())

  def getInputReceivableEntities: List[Entity] = entities.filter(_.components.exists(_.isInstanceOf[InputCom]))

}
