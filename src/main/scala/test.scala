import cats.Monad
import cats.effect.IO

sealed trait Position
case class Pos(x :Int, y: Int) extends Position
case class Move(p1: Position, p2: Position) extends Position

object test extends App {
  val program =
    for {
      _ <- IO(println("Dove vuoi andare?"))
      n <- IO(scala.io.StdIn.readLine())
    } yield ()

  program.unsafeRunSync()
}
