package datamanager

import game_object_system.graphics_objects.Shader
import org.lwjgl.opengl.GL11.{GL_FALSE, GL_TRUE}
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL20.{GL_FRAGMENT_SHADER, GL_VERTEX_SHADER}

object ShaderLoader {

  val SHADER_DIR = "shaders/"

  def loadShaderProgram(vertexFileName : String, fragmentFileName : String) : Option[Shader] = {
    val program = GL20.glCreateProgram()

    val vertexCode = Resource.using(Resource(SHADER_DIR+vertexFileName))(getShaderCode)
    val vertexShader = loadShader(vertexCode, GL_VERTEX_SHADER)
    if (vertexShader == 0) None

    val fragmentCode = Resource.using(Resource(SHADER_DIR+fragmentFileName))(getShaderCode)
    val fragmentShader = loadShader(fragmentCode, GL_FRAGMENT_SHADER)
    if (fragmentShader == 0) None

    GL20.glAttachShader(program, vertexShader)
    GL20.glAttachShader(program, fragmentShader)

    GL20.glLinkProgram(program)

    if (areThereErrors(program, GL20.GL_LINK_STATUS)) {
      None
    }
    else {
      GL20.glValidateProgram(program)
      if (areThereErrors(program, GL20.GL_VALIDATE_STATUS)) {
        None
      }
      else {
        GL20.glDetachShader(program, vertexShader)
        GL20.glDetachShader(program, fragmentShader)
        GL20.glDeleteShader(vertexShader)
        GL20.glDeleteShader(fragmentShader)
        Some(Shader(program))
      }
    }

  }

  private def areThereErrors(program : Int, flag : Int ):Boolean = {
    if (GL20.glGetProgrami(program, flag) == GL_FALSE) {

      flag match {
        case GL20.GL_LINK_STATUS => Console.err.println("Failed to link shader program!")
        case GL20.GL_VALIDATE_STATUS => Console.err.println("Failed to validate shader program!")
      }
      Console.err.println(GL20.glGetProgramInfoLog(program))

      true
    }
    else {
      false
    }
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
          case GL_FRAGMENT_SHADER => Console.err.println("Failed to compile fragment shader! ")
        }
        Console.err.println(GL20.glGetShaderInfoLog(id))
      }

      id

    case None => 0
  }

}
