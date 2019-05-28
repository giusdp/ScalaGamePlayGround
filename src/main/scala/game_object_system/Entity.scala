package game_object_system

class Entity(){
  var components : List[Component] = List()

  def addComponent(c : Component): Unit = components = c :: components

}

object Entity {
//  private var lastID = 0
//  def genID(): Int = {
//    lastID += 1
//    lastID
//  }

  def apply(): Entity = new Entity()

}
