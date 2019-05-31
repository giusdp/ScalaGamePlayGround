package datamanager

import game_object_system._

import scala.io.{BufferedSource, Source}
import org.json4s._
import org.json4s.native.JsonMethods._

object EntityLoader {

  def createEntitiesFromJSON(filename: String): List[Entity] = parseEntitiesJSON(filename).map(buildEntity)

  def buildEntity(cs : List[Component]): Entity = {
    val e = ECHandler.spawnEntity()
    cs.foreach(ECHandler.addComponent(e, _))
    e
  }

  def parseEntitiesJSON(filename: String): List[List[Component]] = {
    val r = new Resource(filename)
    using(r)(prepareComponents)
  }

  def prepareComponents(r: ResourceLoadResult) : List[List[Component]]  = r match {
    case Result(res) =>
      jsonStrToMap(res.mkString).map(componentMapToLists).toList
    case Error(msg) => println(msg) ; List()
  }

  def componentMapToLists(e : (String, Any)) : List[Component] = {
    val cmap = e._2.asInstanceOf[Map[String, Map[String, Any]]]
    cmap.map(asComponent).toList
  }

  /** Heart of the json map to single component transformation. Each described component in the json file is
    * opportunely converted in the associated component. For new components added this method has to be properly extended. */
  def asComponent(c : (String, Map[String, Any])) : Component = c._1 match {
    case "position" => PositionCom(c._2("x").toString.toDouble, c._2("y").toString.toDouble)
    case "movable" => MovableCom(c._2("velX").toString.toDouble, c._2("velX").toString.toDouble)
    case "input" => InputCom()
    case "graphics" => GraphicsCom()
    case _ => EmptyCom()
  }

  def jsonStrToMap(jsonStr: String): Map[String, Any] = {
    implicit val formats: DefaultFormats.type = org.json4s.DefaultFormats
    parse(jsonStr).extract[Map[String, Any]]
  }

  /** Creates the component list for an entity with the components specified in the input list
    * cs: List with the names of the components and the parameters */
  def createComponentList[T](cs: List[(String, List[T])]): List[Component] = cs.map(p => makeComponent(p._1, p._2))


  def makeComponent[T](name: String, args: List[T]): Component = name match {
    case "position" => PositionCom(args.head.asInstanceOf[Double], args(1).asInstanceOf[Double])
    case "movable" => MovableCom(args.head.asInstanceOf[Double], args(1).asInstanceOf[Double])
    case "input" => InputCom()
    case _ => EmptyCom()
  }

  def using[A](r: Resource)(f: ResourceLoadResult => A): A =
    try {
      f(r.load())
    }
    finally {
      r.dispose()
    }

}

sealed trait ResourceLoadResult
case class Result(res : BufferedSource) extends ResourceLoadResult
case class Error(msg : String) extends ResourceLoadResult


class Resource(fn : String) {

  var bs : BufferedSource = _

  def load(): ResourceLoadResult = {
    try {
      bs = Source.fromFile("resources/" + fn)
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
