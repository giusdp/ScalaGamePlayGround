package simulation

object MovementStateMachine {

  var currentState: MovementState = Standing

  def update(key : Int): Unit =  currentState.inputChanged(key)

  def changeState(newState : MovementState): Unit = {
    currentState.exit()
    currentState = newState
    currentState.enter()
  }



}
