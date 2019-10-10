package me.ftvkyo.fuzzy_broccoli.client.graphics.opengl

import org.lwjgl.opengl.GL33.*
import org.lwjgl.system.MemoryStack
import java.util.*


/**
 * Abstraction for OpenGL shader program.
 * Supports vertex shader and fragment shader.
 */
class ShaderProgram(vertexShaderSource: String, fragmentShaderSource: String) : AutoCloseable {

    val id: Int


    init {
        // vertex shader
        val vertexShader = glCreateShader(GL_VERTEX_SHADER)
        glShaderSource(vertexShader, vertexShaderSource)
        glCompileShader(vertexShader)
        checkShaderCompilation(vertexShader)

        // fragment shader
        val fragmentShader = glCreateShader(GL_FRAGMENT_SHADER)
        glShaderSource(fragmentShader, fragmentShaderSource)
        glCompileShader(fragmentShader)
        checkShaderCompilation(fragmentShader)

        // link shaders
        id = glCreateProgram()
        glAttachShader(id, vertexShader)
        glAttachShader(id, fragmentShader)
        glLinkProgram(id)
        checkProgramLinking(id)

        glDeleteShader(vertexShader)
        glDeleteShader(fragmentShader)
    }


    /**
     * Enable this shader program.
     *
     * @return this
     */
    fun use(): ShaderProgram {
        glUseProgram(id)
        return this
    }


    /**
     * Disable current shader program.
     */
    fun reset() {
        glUseProgram(0)
    }


    override fun close() {
        glDeleteProgram(id)
    }

    companion object {

        /**
         * Path to resource of default 2D vertex shader.
         */
        const val default2DVertexShaderResource = "me/ftvkyo/fuzzy_broccoli/client/graphics/opengl/shader-2d.vert"

        /**
         * Path to resource of default 2D fragment shader.
         */
        const val default2DFragmentShaderResource = "me/ftvkyo/fuzzy_broccoli/client/graphics/opengl/shader.frag"

        /**
         * Path to resource of default 3D vertex shader.
         */
        const val default3DVertexShaderResource = "me/ftvkyo/fuzzy_broccoli/client/graphics/opengl/shader-3d.vert"

        /**
         * Path to resource of default 3D fragment shader.
         */
        const val default3DFragmentShaderResource = default2DFragmentShaderResource


        fun fromResources(vertexShaderPath: String, fragmentShaderPath: String): ShaderProgram {
            val cl = Thread.currentThread().contextClassLoader

            cl.getResourceAsStream(vertexShaderPath)!!.use { vertexShader ->
                cl.getResourceAsStream(fragmentShaderPath)!!.use { fragmentShader ->
                    val vertexS = Scanner(vertexShader).useDelimiter("\\A")
                    val fragmentS = Scanner(fragmentShader).useDelimiter("\\A")
                    return ShaderProgram(vertexS.next(), fragmentS.next())
                }
            }

        }


        /**
         * Check if given shader compilation was successful and throw an exception if not.
         *
         * @param shader shader that we want to check compilation status for
         * @throws RuntimeException when shader compilation was not successful
         */
        @Throws(RuntimeException::class)
        private fun checkShaderCompilation(shader: Int) {
            MemoryStack.stackPush().use { stack ->
                val success = stack.mallocInt(1)

                glGetShaderiv(shader, GL_COMPILE_STATUS, success)
                if (success.get() == 0) {
                    val infoLog = glGetShaderInfoLog(shader)
                    throw RuntimeException("Shader compilation failed: $infoLog")
                }
            }
        }


        /**
         * Check if given program linking was successful and throw an exception if not.
         *
         * @param program program that we want to check linking status for
         * @throws RuntimeException when program linking was not successful
         */
        @Throws(RuntimeException::class)
        private fun checkProgramLinking(program: Int) {
            MemoryStack.stackPush().use { stack ->
                val success = stack.mallocInt(1)

                glGetProgramiv(program, GL_LINK_STATUS, success)
                if (success.get() == 0) {
                    val infoLog = glGetProgramInfoLog(program)
                    throw RuntimeException("Shader program linking failed: $infoLog")
                }
            }
        }
    }
}
