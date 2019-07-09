package datamanager

import game_object_system.graphics_objects.Shader
import org.lwjgl.opengl.GL11.{GL_FALSE, GL_TRUE}
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL20.{GL_FRAGMENT_SHADER, GL_VERTEX_SHADER}
import org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER

object ShaderLoader {

  val SHADER_DIR = "shaders/"

  def loadShaderProgram(vertexFileName : String, fragmentFileName : String) : Option[Shader] = {
    val program = GL20.glCreateProgram()

    val vertexShader = loadShader(vertexFileName, GL_VERTEX_SHADER)
    val fragmentShader = loadShader(fragmentFileName, GL_FRAGMENT_SHADER)

    GL20.glAttachShader(program, vertexShader)
    GL20.glAttachShader(program, fragmentShader)

    val shader = link(program)
    cleanUp(program, vertexShader,fragmentShader)
    shader
  }

  def loadShaderProgram(vertexFileName : String, geometryFileName : String, fragmentFileName : String) : Option[Shader] = {
    val program = GL20.glCreateProgram()

    val vertexShader = loadShader(vertexFileName, GL_VERTEX_SHADER)
    val geometryShader = loadShader(geometryFileName, GL_GEOMETRY_SHADER)
    val fragmentShader = loadShader(fragmentFileName, GL_FRAGMENT_SHADER)

    GL20.glAttachShader(program, vertexShader)
    GL20.glAttachShader(program, geometryShader)
    GL20.glAttachShader(program, fragmentShader)

    val shader = link(program)
    cleanUp(program, vertexShader, geometryShader, fragmentShader)

    shader
  }

  def link(program : Int): Some[Shader] = {
    GL20.glLinkProgram(program)
    if (areThereErrors(program, GL20.GL_LINK_STATUS)) None
    GL20.glValidateProgram(program)
    if (areThereErrors(program, GL20.GL_VALIDATE_STATUS)) None
    Some(Shader(program))
  }

  private def areThereErrors(program : Int, flag : Int ):Boolean = {
    if (GL20.glGetProgrami(program, flag) == GL_FALSE) {
      flag match {
        case GL20.GL_LINK_STATUS => Console.err.println("Failed to link shader program!")
        case GL20.GL_VALIDATE_STATUS => Console.err.println("Failed to validate shader program!")
      }
      Console.err.println(GL20.glGetProgramInfoLog(program))
      true
    } else false
  }

  private def getShaderCode(r: ResourceLoadResult) : Option[String] = r match {
    case Result(res) => Some(res.mkString + "\n")
    case Error(msg) => Console.err.println(msg) ; None
  }

  private def loadShader(s : Option[String], shaderType: Int) : Int = s match {
    case Some(code) =>
      val id = GL20.glCreateShader(shaderType)
      GL20.glShaderSource(id, code)
      GL20.glCompileShader(id)

      if (GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL_FALSE) {
        shaderType match {
          case GL_VERTEX_SHADER => Console.err.println("Failed to compile vertex shader! ")
          case GL_GEOMETRY_SHADER => Console.err.println("Failed to compile geometry shader! ")
          case GL_FRAGMENT_SHADER => Console.err.println("Failed to compile fragment shader! ")
        }
        Console.err.println(GL20.glGetShaderInfoLog(id))
      }
      id
    case None => 0
  }

  private def loadShader(fn : String, shaderType : Int): Int =
    loadShader(Resource.using(Resource(SHADER_DIR+fn))(getShaderCode), shaderType)


  private def cleanUp(program : Int, vs : Int, fs : Int): Unit = {
    GL20.glDetachShader(program, vs)
    GL20.glDetachShader(program, fs)
    GL20.glDeleteShader(vs)
    GL20.glDeleteShader(fs)
  }
  private def cleanUp(program : Int, vs : Int, gs : Int, fs: Int): Unit = {
    cleanUp(program, vs, fs)
    GL20.glDetachShader(program, gs)
    GL20.glDeleteShader(gs)
  }

}
