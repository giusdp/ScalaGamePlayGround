package state_system

sealed trait Component

final case class PositionCom(var x: Int, var y: Int) extends Component

final case class InputCom() extends Component