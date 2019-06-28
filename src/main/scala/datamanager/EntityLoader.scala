package datamanager

import com.badlogic.ashley.core.{Component, Entity}
import game_object_system._
import org.json4s._
import org.json4s.native.JsonMethods._

object EntityLoader {

  def createEntitiesFromJSON(filename: String): List[Entity] = parseJSON(filename).map(buildEntity)

  private def buildEntity(cs : List[Component]): Entity = {
    val e = new Entity()
    cs.filter(!_.isInstanceOf[EmptyCom]).foreach(c => e.add(c))
    e
  }

  /** Parse file json with entities encoded in, return a list of components to build each entity*/
  private def parseJSON(filename: String): List[List[Component]] = {
    Resource.using(Resource(filename))(prepareComponents)
  }


  /** Takes the result of the parsing and makes a Map that describe components, return the list of component
    * for each entity. */
  private def prepareComponents(r: ResourceLoadResult) : List[List[Component]]  = r match {
    case Result(res) =>
      jsonStrToMap(res.mkString).map(componentMapToLists _).toList
    case Error(msg) => Console.err.println(msg) ; List()
  }

  /** Json string to Map[Component name, data] */
  private def jsonStrToMap(jsonStr: String): Map[String, Any] = {
    implicit val formats: DefaultFormats.type = org.json4s.DefaultFormats
    parse(jsonStr).extract[Map[String, Any]]
  }

  /** Given a map component name -> data, returns a list of Components*/
  private def componentMapToLists(e : (String, Any)) : List[Component] = {
    val cmap = e._2.asInstanceOf[Map[String, Map[String, Any]]]
    cmap.map(asComponent _).toList
  }

  private def extractRenderableCom(map : Map[String, Any]): Component =
    SpriteLoader.loadSprite(map("sprite").toString) match {
      case Some(s) => RenderableCom(s)
      case _ => EmptyCom()
    }

  /**  Each described component in the json file is  converted in the associated component.
    * For new components added this method has to be properly extended. */
  private def asComponent(c : (String, Map[String, Any])) : Component = c._1 match {
    case "position" => PositionCom(c._2("x").toString.toFloat, c._2("y").toString.toFloat, 0)
    case "velocity" => VelocityCom(c._2("value").toString.toFloat)
    case "msm" => MovementSMCom()
    case "renderable" => extractRenderableCom(c._2)
    case "camera_center" => CameraCenterCom()
    case _ => EmptyCom()
  }
}
