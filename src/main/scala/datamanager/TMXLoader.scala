package datamanager

import java.awt.Rectangle

import game_object_system.{TMXLayer, TMXMap, TMXObject, TMXObjectGroup, TmxTileset}

import scala.xml.{Node, XML}

object TMXLoader {
  val DATA = "data"
  val IMAGE = "image"
  val LAYER = "layer"
  val OBJECT = "object"
  val OBJECT_GROUP = "objectgroup"
  val TILE_SET = "tileset"
  val TILE = "tile"
  val FIRST_GID = "@firstgid"
  val GID = "@gid"
  val NAME = "@name"
  val SOURCE = "@source"
  val Y = "@y"
  val X = "@x"
  val WIDTH = "@width"
  val HEIGHT = "@height"
  val TILE_WIDTH = "@tilewidth"
  val TILE_HEIGHT = "@tileheight"
  val IMAGE_SOURCE = "@image source"

  def parseTSX(fileName: String): TmxTileset = {
    val xml = XML.loadFile("/home/giuseppe/Repositories/SGExp/resources/"+fileName)
    TmxTileset((xml \ NAME).text,getInt((xml \ TILE_WIDTH).text),getInt((xml \ TILE_HEIGHT).text),(xml \ IMAGE_SOURCE).text)
  }

  def parseTMX(fileName: String): TMXMap = {
    val xml = XML.loadFile(fileName)
    val width: Int = getInt((xml \ WIDTH).text)
    val height: Int = getInt((xml \ HEIGHT).text)
    val tileWidth: Int = getInt((xml \ TILE_WIDTH).text)
    val tileHeight: Int = getInt((xml \ TILE_HEIGHT).text)

    val tileSet: Seq[TmxTileset] = (xml \ TILE_SET) collect {case ts: Node => parseTSX((ts \ SOURCE).text)}

    val layers: Seq[TMXLayer] = (xml \ LAYER) collect { case layer: Node =>
      TMXLayer((layer \ NAME).text, getInt((layer \ WIDTH).text),getInt((layer \ HEIGHT).text), (layer \ DATA).text)
    }

    val objectGroup: Seq[TMXObjectGroup] = (xml \ OBJECT_GROUP) collect { case objectGroup: Node =>
      val objects: Seq[TMXObject] = objectGroup \ OBJECT collect { case o: Node =>
        val x = getInt((o \ X).text)
        val y = getInt((o \ Y).text)
        val w = getInt((o \ WIDTH).text)
        val h = getInt((o \ HEIGHT).text)
        TMXObject(getInt((o \ GID).text), new Rectangle(x, y, w, h))
      }
      TMXObjectGroup((objectGroup \ NAME).text, getInt((objectGroup \ WIDTH).text),
        getInt((objectGroup \ HEIGHT).text), objects.toList)
    }

    TMXMap(width, height, tileWidth, tileHeight, tileSet, layers, objectGroup)
  }

  val getInt: String => Int = (s: String) => if (s.nonEmpty) s.toFloat.toInt else -1
}