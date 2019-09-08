package me.ftvkyo.fuzzy_broccoli.client.controller;

import me.ftvkyo.fuzzy_broccoli.client.view.DrawableMainMenu;
import me.ftvkyo.fuzzy_broccoli.client.view.Screen;
import me.ftvkyo.fuzzy_broccoli.common.model.World;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.NULL;


/**
 * Window class is a "Controller" part in MVC
 */
public class Window implements AutoCloseable {

    private static final int FPS = 60;

    private static final int MSPF = 1000 / FPS;

    private static final String vertexShaderSource = "" +
            "#version 330 core\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "void main()\n" +
            "{\n" +
            "   gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);\n" +
            "}";

    private static final String fragmentShaderSource = "" +
            "#version 330 core\n" +
            "out vec4 FragColor;\n" +
            "void main()\n" +
            "{\n" +
            "   FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);\n" +
            "}\n";

    private Screen screen;

    private World world;

    private long window;


    public Window() {
        this.world = null;

        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()) {
            throw new RuntimeException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(800, 600, "fuzzy-broccoli", NULL, NULL);
        if(window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        this.screen = new Screen(DrawableMainMenu.getInstance());
        InputProcessor.setForWindow(window, new InputProcessorMainMenu(screen));
    }


    public void resetWorld(World world) {
        if(this.world != null) {
            this.world.close();
        }
        this.world = world;
    }


    public void run() {
        glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            glViewport(0, 0, width, height);
        });

        glfwSetWindowSizeCallback(window, (window, width, height) -> {
            glViewport(0, 0, width, height);
        });

        int shaderProgram = createShaderProgram(window);

        glfwSwapInterval(1);
        glfwShowWindow(window);

        glUseProgram(shaderProgram);

        try {
            while(!glfwWindowShouldClose(window)) {
                if(this.world != null) {
                    this.world.tick();
                }

                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                this.screen.update(this.world);

                glfwSwapBuffers(window);
                glfwPollEvents();

                Thread.sleep(MSPF);
            }
        } catch(InterruptedException e) {
            System.out.println("Thread was interrupted.");
        }
    }


    private int createShaderProgram(long windows) {
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);

        int shaderProgram = 0;

        glShaderSource(vertexShader, vertexShaderSource);
        glCompileShader(vertexShader);
        // check for shader compile errors
        try(MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer success = stack.mallocInt(3);
            IntBuffer infoLogLength = stack.mallocInt(1);
            ByteBuffer infoLog = stack.malloc(512);

            glGetShaderiv(vertexShader, GL_COMPILE_STATUS, success);
            if(success.get(0) == 0) {
                glGetShaderInfoLog(vertexShader, infoLogLength, infoLog);
                byte[] bytes = new byte[infoLogLength.get(0)];
                infoLog.get(bytes);
                String v = new String(bytes, StandardCharsets.US_ASCII);
                throw new RuntimeException("ERROR::SHADER::VERTEX::COMPILATION_FAILED: " + v);
            }

            // fragment shader
            glShaderSource(fragmentShader, fragmentShaderSource);
            glCompileShader(fragmentShader);
            // check for shader compile errors
            glGetShaderiv(fragmentShader, GL_COMPILE_STATUS, success);
            if(success.get(1) == 0) {
                glGetShaderInfoLog(fragmentShader, infoLogLength, infoLog);
                byte[] bytes = new byte[infoLogLength.get(0)];
                infoLog.get(bytes);
                String v = new String(bytes, StandardCharsets.US_ASCII);
                throw new RuntimeException("ERROR::SHADER::FRAGMENT::COMPILATION_FAILED: " + v);
            }
            // link shaders
            shaderProgram = glCreateProgram();

            glAttachShader(shaderProgram, vertexShader);
            glAttachShader(shaderProgram, fragmentShader);
            glLinkProgram(shaderProgram);
            // check for linking errors
            glGetProgramiv(shaderProgram, GL_LINK_STATUS, success);
            if(success.get(2) == 0) {
                glGetProgramInfoLog(shaderProgram, infoLogLength, infoLog);
                byte[] bytes = new byte[infoLogLength.get(0)];
                infoLog.get(bytes);
                String v = new String(bytes, StandardCharsets.US_ASCII);
                throw new RuntimeException("ERROR::SHADER::PROGRAM::LINKING_FAILED: " + v);
            }
        }

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);

        return shaderProgram;
    }


    @Override
    public void close() {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();

        resetWorld(null);
    }
}
