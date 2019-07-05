package datamanager

import java.awt.Rectangle

import game_object_system.graphics_objects.{CustomTextureRect, Model, ObjectLayer, ObjectShape, TextureAtlas, TileLayer, TileMap}

import scala.xml.{Node, NodeSeq, XML}

object TMXLoader {
  private val DATA = "data"
  private val IMAGE = "image"
  private val LAYER = "layer"
  private val OBJECT = "object"
  private val OBJECT_GROUP = "objectgroup"
  private val TILE_SET = "tileset"
  private val TILE = "tile"
  private val FIRST_GID = "@firstgid"
  private val GID = "@gid"
  private val NAME = "@name"
  private val SOURCE = "@source"
  private val Y = "@y"
  private val X = "@x"
  private val WIDTH = "@width"
  private val HEIGHT = "@height"
  private val TILE_WIDTH = "@tilewidth"
  private val TILE_HEIGHT = "@tileheight"

  private def parseTSX(fileName: String): TextureAtlas = {
    val xml = XML.loadFile(Resource.RES_DIR+fileName)
    val imageFileName = (xml \ IMAGE \ SOURCE).text
    val tw = getInt((xml \ TILE_WIDTH).text)
    val th = getInt((xml \ TILE_HEIGHT).text)
    TextureLoader.loadTextureAtlas(imageFileName, tw, th)
  }

  def parseTMX(fileName: String): TileMap = {
//    println("Parsing " + fileName)
    val xml = XML.loadFile(Resource.RES_DIR+fileName)
//    println("XML loaded")

    val width: Int = getInt((xml \ WIDTH).text)
    val height: Int = getInt((xml \ HEIGHT).text)
    val tileWidth: Int = getInt((xml \ TILE_WIDTH).text)
    val tileHeight: Int = getInt((xml \ TILE_HEIGHT).text)

//    println("Loaded attributes: width " + width + ", height " + height+", tileWidth " + tileWidth+", tileHeight "+tileHeight)

    val tileSet: TextureAtlas = ((xml \ TILE_SET) collectFirst {case ts: Node => parseTSX((ts \ SOURCE).text)}).get
//    println("TileSet (textureAtlas) loaded: tW " +tileSet.imageWidth + ", tH " + tileSet.imageHeight )

    val tileLayers: Seq[TileLayer] = prepareTileLayers(xml \ LAYER, tileWidth, tileHeight, tileSet)
//    println("Layers loaded: nl " + tileLayers.length + ", numberTiles each layer: " + tileLayers.head.tiles.length)

    val objectLayers: Seq[ObjectLayer] = prepareObjectLayers(xml \ OBJECT_GROUP)
//    println("Objects (rectangles) loaded: nr " + objectLayers.head.objects.length)

//    println("TileMap loaded")
    TileMap(width, height, tileWidth, tileHeight, tileSet, tileLayers, objectLayers)
  }







  private val getInt: String => Int = (s: String) => if (s.nonEmpty) s.toFloat.toInt else -1

  private def prepareTileLayers(layers : NodeSeq, tileWidth : Float, tileHeight: Float, atlas: TextureAtlas) : Seq[TileLayer] = layers collect {
    case layer: Node =>
      val layerWidth = getInt((layer \ WIDTH).text)
      val layerHeight = getInt((layer \ HEIGHT).text)
      val data: Array[Int] = (layer \ DATA).text.split(",").map(_.trim.toInt)

      val tiles: Seq[Model] = for {
        y <- 0 until layerHeight
        x <- 0 until layerWidth
        if data(x + (y*layerHeight)) != 0
      } yield buildTile(x*tileWidth, y*tileHeight, tileWidth, tileHeight, data(x + (y*layerHeight)), atlas)
//        .filterNot(_==0)
//        .map(t => buildTile(tileWidth, tileHeight, t, atlas))

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