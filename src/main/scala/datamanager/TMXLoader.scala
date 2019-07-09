package datamanager

import java.awt.Rectangle

import game_object_system.graphics_objects.{CustomTextureRect, Model, ObjectLayer, ObjectShape, TextureAtlas, Tile, TileLayer, TileMap, TileSet}

import scala.xml.{Node, NodeSeq, XML}

object TMXLoader {
  private val DATA = "data"
  private val IMAGE = "image"
  private val LAYER = "layer"
  private val OBJECT = "object"
  private val OBJECT_GROUP = "objectgroup"
  private val TILE_SET = "tileset"
//  private val TILE = "tile"
//  private val FIRST_GID = "@firstgid"
  private val GID = "@gid"
  private val NAME = "@name"
  private val SOURCE = "@source"
  private val Y = "@y"
  private val X = "@x"
  private val WIDTH = "@width"
  private val HEIGHT = "@height"
  private val TILE_WIDTH = "@tilewidth"
  private val TILE_HEIGHT = "@tileheight"
  private val TILE_COUNT = "@tilecount"

  private def parseTSX(fileName: String): TileSet = {
    val xml = XML.loadFile(Resource.RES_DIR+fileName)
    val imageFileName: String = (xml \ IMAGE \ SOURCE).text
    val tw: Int = getInt((xml \ TILE_WIDTH).text)
    val th: Int = getInt((xml \ TILE_HEIGHT).text)
    val tc: Int = getInt((xml \ TILE_COUNT).text)
    val ta: TextureAtlas = TextureLoader.loadTextureAtlas(imageFileName, tw, th)
    val tiles: Map[Int, Tile] = (0 until tc).map(i => i -> Tile(ta.extractRegion(i))).toMap
    TileSet(tiles, ta)
  }

  def parseTMX(fileName: String): TileMap = {
//    println("Parsing " + fileName)
    val xml = XML.loadFile(Resource.RES_DIR+fileName)

    val width: Int = getInt((xml \ WIDTH).text)
    val height: Int = getInt((xml \ HEIGHT).text)
    val tileWidth: Int = getInt((xml \ TILE_WIDTH).text)
    val tileHeight: Int = getInt((xml \ TILE_HEIGHT).text)

    val tileSet: TileSet = ((xml \ TILE_SET) collectFirst {case ts: Node => parseTSX((ts \ SOURCE).text)}).get

    val tileLayers: Seq[TileLayer] = prepareTileLayers(xml \ LAYER, tileWidth, tileHeight, tileSet.textureAtlas)

    val objectLayers: Seq[ObjectLayer] = prepareObjectLayers(xml \ OBJECT_GROUP)

    TileMap(width, height, tileWidth, tileHeight, tileSet, tileLayers, objectLayers)
  }

  private val getInt: String => Int = (s: String) => if (s.nonEmpty) s.toFloat.toInt else -1

  private def prepareTileLayers(layers : NodeSeq, tileWidth : Float, tileHeight: Float, atlas: TextureAtlas) : Seq[TileLayer] = layers collect {
    case layer: Node =>
      val layerWidth = getInt((layer \ WIDTH).text)
      val layerHeight = getInt((layer \ HEIGHT).text)
      val data: Array[Int] = (layer \ DATA).text.split(",").map(_.trim.toInt)

      var tiles : List[Model]= List()
      for(y <-0 until layerHeight) for (x <- 0 until layerWidth){
        if (data(x + (y*layerHeight)) != 0)
          tiles = buildTile(x*tileWidth, -y*tileHeight, tileWidth, tileHeight, data(x + (y*layerHeight)), atlas) :: tiles
      }
      TileLayer((layer \ NAME).text, layerWidth, layerHeight, tiles.toArray)
  }

  private def buildTile(x : Float, y : Float, tw: Float, th: Float, id: Int, atlas: TextureAtlas): Model = {
    val tcs = atlas.extractRegion(id)
    ModelLoader.loadModel(CustomTextureRect(x, y, tw, th, tcs))
  }

  private def prepareObjectLayers(groups : NodeSeq) : Seq[ObjectLayer] = groups collect {
    case objectGroup: Node =>
      val objects: Seq[ObjectShape] = objectGroup \ OBJECT collect { case o: Node =>
        val x = getInt((o \ X).text)
        val y = getInt((o \ Y).text)
        val w = getInt((o \ WIDTH).text)
        val h = getInt((o \ HEIGHT).text)
        ObjectShape(getInt((o \ GID).text), new Rectangle(x, y, w, h))
      }
      ObjectLayer((objectGroup \ NAME).text, getInt((objectGroup \ WIDTH).text),
        getInt((objectGroup \ HEIGHT).text), objects.toList)
  }

}