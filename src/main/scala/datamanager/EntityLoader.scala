package datamanager

import game_object_system._
import org.json4s._
import org.json4s.native.JsonMethods._

object EntityLoader {

  /** Heart of the json map to single component transformation. Each described component in the json file is
    * opportunely converted in the associated component. For new components added this method has to be properly extended. */
  def asComponent(c : (String, Map[String, Any])) : Component = c._1 match {
    case "position" => PositionCom(c._2("x").toString.toDouble, c._2("y").toString.toDouble)
    case "movable" => MovableCom(c._2("velX").toString.toDouble, c._2("velX").toString.toDouble)
    case "input" => InputCom()
    case "renderable" => RenderableCom()
    case _ => EmptyCom()
  }

  def createEntitiesFromJSON(filename: String): List[Entity] = parseJSON(filename).map(buildEntity)

  def buildEntity(cs : List[Component]): Entity = {
    val e = ECHandler.spawnEntity()
    cs.foreach(ECHandler.addComponent(e, _))
    e
  }

  def parseJSON(filename: String): List[List[Component]] = {
    Resource.using(Resource(filename))(prepareComponents)
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


  def jsonStrToMap(jsonStr: String): Map[String, Any] = {
    implicit val formats: DefaultFormats.type = org.json4s.DefaultFormats
    parse(jsonStr).extract[Map[String, Any]]
  }

}
