package state_system

class Entity {

  val ID : Int = Entity.genID()

  var components : List[Component] = _

  def addComponent(c : Component): Unit = components = components ++ List(c)

  def update(): Unit = {

  }

}

object Entity {
  var lastID = 0
  def genID(): Int = {
    lastID += 1
    lastID
  }
}
