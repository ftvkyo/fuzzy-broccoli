package me.ftvkyo.fuzzy_broccoli.client.graphics.opengl;

import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;


/**
 * Abstraction for OpenGL shader program.
 * Supports vertex shader and fragment shader.
 */
public class ShaderProgram implements AutoCloseable {

    /**
     * Path to resource of default vertex shader.
     */
    public static final String defaultVertexShaderResource = "me/ftvkyo/fuzzy_broccoli/client/opengl/shader.vert";

    /**
     * Path to resource of default fragment shader.
     */
    public static final String defaultFragmentShaderResource = "me/ftvkyo/fuzzy_broccoli/client/opengl/shader.frag";

    private final int shaderProgram;


    /**
     * Create shader program from given sources of vertex shader and fragment shader.
     *
     * @param vertexShaderSource   source code of GLSL vertex shader.
     * @param fragmentShaderSource source code of GLSL fragment shader.
     */
    public ShaderProgram(String vertexShaderSource,
                         String fragmentShaderSource) {
        // vertex shader
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexShaderSource);
        glCompileShader(vertexShader);
        checkShaderCompilation(vertexShader);

        // fragment shader
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentShaderSource);
        glCompileShader(fragmentShader);
        checkShaderCompilation(fragmentShader);

        // link shaders
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);
        checkProgramLinking(shaderProgram);

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }


    /**
     * Check if given shader compilation was successful and throw an exception if not.
     *
     * @param shader shader that we want to check compilation status for
     * @throws RuntimeException when shader compilation was not successful
     */
    private static void checkShaderCompilation(int shader) throws RuntimeException {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer success = stack.mallocInt(1);

            glGetShaderiv(shader, GL_COMPILE_STATUS, success);
            if(success.get() == 0) {
                String infoLog = glGetShaderInfoLog(shader);
                throw new RuntimeException("Shader compilation failed: " + infoLog);
            }
        }
    }


    /**
     * Check if given program linking was successful and throw an exception if not.
     *
     * @param program program that we want to check linking status for
     * @throws RuntimeException when program linking was not successful
     */
    private static void checkProgramLinking(int program) throws RuntimeException {
        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer success = stack.mallocInt(1);

            glGetProgramiv(program, GL_LINK_STATUS, success);
            if(success.get() == 0) {
                String infoLog = glGetProgramInfoLog(program);
                throw new RuntimeException("Shader program linking failed: " + infoLog);
            }
        }
    }


    /**
     * Enable this shader program.
     *
     * @return this
     */
    public ShaderProgram use() {
        glUseProgram(shaderProgram);
        return this;
    }


    /**
     * Get OpenGL id of this shader program.
     *
     * @return shader program id
     */
    public int getID() {
        return shaderProgram;
    }


    @Override
    public void close() {
        glDeleteProgram(shaderProgram);
    }
}
