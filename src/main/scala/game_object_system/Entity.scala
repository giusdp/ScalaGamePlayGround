package game_object_system

object Entity {
  private var lastID = 0
  def spawnEntity(): Int = {
    lastID += 1
    lastID
  }

}
