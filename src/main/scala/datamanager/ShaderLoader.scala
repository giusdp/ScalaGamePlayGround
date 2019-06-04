package datamanager

import game_object_system.Shader
import org.lwjgl.opengl.GL11.{GL_FALSE, GL_TRUE}
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL20.{GL_FRAGMENT_SHADER, GL_VERTEX_SHADER}

object ShaderLoader {

  def loadShaderProgram(vertexFileName : String, fragmentFileName : String) : Option[Shader] = {
    val vertexCode = Resource.using(Resource(vertexFileName))(getShaderCode)
    val vertexShader = loadShader(vertexCode, GL_VERTEX_SHADER)
    if (vertexShader == 0) None

    val fragmentCode = Resource.using(Resource(fragmentFileName))(getShaderCode)
    val fragmentShader = loadShader(fragmentCode, GL_FRAGMENT_SHADER)
    if (fragmentShader == 0) None

    val program = GL20.glCreateProgram()
    GL20.glAttachShader(program, vertexShader)
    GL20.glAttachShader(program, fragmentShader)
    GL20.glLinkProgram(program)
    if (GL20.glGetProgrami(program, GL20.GL_LINK_STATUS) == GL_FALSE){
      Console.err.println(GL20.glGetProgramInfoLog(program))
      GL20.glDeleteShader(vertexShader)
      GL20.glDeleteShader(fragmentShader)
      GL20.glDeleteProgram(program)
      None
    }

    GL20.glValidateProgram(program)
    if (GL20.glGetProgrami(program, GL20.GL_VALIDATE_STATUS) == GL_TRUE){
      Console.err.println(GL20.glGetProgramInfoLog(program))
      GL20.glDeleteShader(vertexShader)
      GL20.glDeleteShader(fragmentShader)
      GL20.glDeleteProgram(program)
      None
    }

    GL20.glDetachShader(program, vertexShader)
    GL20.glDetachShader(program, fragmentShader)
//    GL20.glDeleteShader(vertexShader)
//    GL20.glDeleteShader(fragmentShader)
    Some(Shader(program))
  }

  private def getShaderCode(r: ResourceLoadResult) : Option[String] = r match {
    case Result(res) => Some(res.mkString)
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
