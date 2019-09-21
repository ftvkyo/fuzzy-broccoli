package me.ftvkyo.fuzzy_broccoli.client.graphics.opengl;

import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import java.util.Scanner;

import static org.lwjgl.opengl.GL33.*;


/**
 * Abstraction for OpenGL shader program.
 * Supports vertex shader and fragment shader.
 */
public class ShaderProgram implements AutoCloseable {

    /**
     * Path to resource of default 2D vertex shader.
     */
    public static final String default2DVertexShaderResource = "me/ftvkyo/fuzzy_broccoli/client/graphics/opengl/shader-2d.vert";

    /**
     * Path to resource of default 2D fragment shader.
     */
    public static final String default2DFragmentShaderResource = "me/ftvkyo/fuzzy_broccoli/client/graphics/opengl/shader.frag";

    /**
     * Path to resource of default 3D vertex shader.
     */
    public static final String default3DVertexShaderResource = "me/ftvkyo/fuzzy_broccoli/client/graphics/opengl/shader-3d.vert";

    /**
     * Path to resource of default 3D fragment shader.
     */
    public static final String default3DFragmentShaderResource = default2DFragmentShaderResource;

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


    public static ShaderProgram fromResources(String vertexShaderPath, String fragmentShaderPath) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        try(InputStream vertexShader = cl.getResourceAsStream(vertexShaderPath);
            InputStream fragmentShader = cl.getResourceAsStream(fragmentShaderPath)) {
            if(vertexShader == null || fragmentShader == null) {
                throw new RuntimeException("Shader not found.");
            }

            Scanner vertexS = new Scanner(vertexShader).useDelimiter("\\A");
            Scanner fragmentS = new Scanner(fragmentShader).useDelimiter("\\A");

            return new ShaderProgram(vertexS.next(), fragmentS.next()).use();
        } catch(IOException e) {
            throw new RuntimeException("Could not open shader file.", e);
        }
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
     * Disable current shader program.
     */
    public void reset() {
        glUseProgram(0);
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
