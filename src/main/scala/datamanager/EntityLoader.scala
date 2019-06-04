package datamanager

import game_object_system._
import game_object_system.graphics_objects.{Quad, SpriteCom}
import org.json4s._
import org.json4s.native.JsonMethods._

object EntityLoader {

  /** Heart of the json map to single component transformation. Each described component in the json file is
    * opportunely converted in the associated component. For new components added this method has to be properly extended. */
  private def asComponent(c : (String, Map[String, Any])) : Component = c._1 match {
    case "position" => PositionCom(c._2("x").toString.toFloat, c._2("y").toString.toFloat)
    case "movable" => MovableCom(c._2("velX").toString.toFloat, c._2("velX").toString.toFloat)
    case "input" => InputCom()
    case "renderable" => RenderableCom()
    case "sprite" => extractSprite(c._2)
    case _ => EmptyCom()
  }

  def extractSprite(info : Map[String, Any]) : Component = info("shape").toString match {
    case "quad" => SpriteLoader.loadQuadSprite(info("texture").toString).getOrElse(EmptyCom())
    case _ => Console.err.println("Shape extraction error when reading file entity json.") ; EmptyCom()
  }

  def createEntitiesFromJSON(filename: String): List[Entity] = parseJSON(filename).map(buildEntity)

  private def buildEntity(cs : List[Component]): Entity = {
    val e = ECHandler.spawnEntity()
    cs.filter(!_.isInstanceOf[EmptyCom]).sorted.foreach(ECHandler.addComponent(e, _)) // sort for number of com needed and remove all useless emptycom
    e
  }

  private def parseJSON(filename: String): List[List[Component]] = {
    Resource.using(Resource(filename))(prepareComponents)
  }

  private def prepareComponents(r: ResourceLoadResult) : List[List[Component]]  = r match {
    case Result(res) =>
      jsonStrToMap(res.mkString).map(componentMapToLists).toList
    case Error(msg) => Console.err.println(msg) ; List()
  }

  private def componentMapToLists(e : (String, Any)) : List[Component] = {
    val cmap = e._2.asInstanceOf[Map[String, Map[String, Any]]]
    cmap.map(asComponent).toList
  }


  private def jsonStrToMap(jsonStr: String): Map[String, Any] = {
    implicit val formats: DefaultFormats.type = org.json4s.DefaultFormats
    parse(jsonStr).extract[Map[String, Any]]
  }

}
