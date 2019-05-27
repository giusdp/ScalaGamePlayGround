package state_system

class Entity(val ID : Int) {

  var components : List[Component] = List()

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

  def apply(): Entity = {
    new Entity(genID())
  }
}
