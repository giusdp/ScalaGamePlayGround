package state_system

trait Component{
  def update()
}

case class PositionCom(var x: Int, var y: Int) extends Component {
  override def update(): Unit = ???
}