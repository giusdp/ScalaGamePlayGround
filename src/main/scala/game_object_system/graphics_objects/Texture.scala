package game_object_system.graphics_objects

import org.lwjgl.opengl.GL11

case class Texture (id : Int, w : Float, h : Float) {
  def dispose(): Unit = GL11.glDeleteTextures(id)
}
