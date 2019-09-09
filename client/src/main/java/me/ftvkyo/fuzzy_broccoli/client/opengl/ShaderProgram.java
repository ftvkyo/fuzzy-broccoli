package me.ftvkyo.fuzzy_broccoli.client.opengl;

import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL33.*;


public class ShaderProgram implements AutoCloseable {

    public static final String defaultVertexShaderResource = "me/ftvkyo/fuzzy_broccoli/client/opengl/shader.vert";

    public static final String defaultFragmentShaderResource = "me/ftvkyo/fuzzy_broccoli/client/opengl/shader.frag";

    private final int shaderProgram;


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


    public ShaderProgram use() {
        glUseProgram(shaderProgram);
        return this;
    }


    public int getID() {
        return shaderProgram;
    }


    @Override
    public void close() {
        glDeleteProgram(shaderProgram);
    }
}
