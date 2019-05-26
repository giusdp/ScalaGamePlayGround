package simulation

sealed trait MovementState{
  def enter()
  def exit()
  def inputChanged()
}
case object Standing extends MovementState {
  override def enter(): Unit = ???

  override def exit(): Unit = ???

  override def inputChanged(): Unit = ???
}
case object Walking extends MovementState {
  override def enter(): Unit = ???

  override def exit(): Unit = ???

  override def inputChanged(): Unit = ???
}