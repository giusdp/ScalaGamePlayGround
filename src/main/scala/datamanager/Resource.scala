package datamanager

import scala.io.{BufferedSource, Source}

sealed trait ResourceLoadResult
case class Result(res : BufferedSource) extends ResourceLoadResult
case class Error(msg : String) extends ResourceLoadResult

class Resource(fn : String) {

  var bs : BufferedSource = _

  def load(): ResourceLoadResult = {
    try {
      bs = Source.fromFile(Resource.RES_DIR+ fn)
      Result(bs)
    }
    catch {
      case e : Exception => Error(e.getMessage)
    }
  }

  def dispose(): Unit = {
    if (bs != null) bs.close()
  }
}

object Resource {
  val RES_DIR = "resources/"

  def apply(filename: String): Resource = new Resource(filename)

  def using[A](r: Resource)(f: ResourceLoadResult => A): A =
    try {
      f(r.load())
    }
    finally {
      r.dispose()
    }
}