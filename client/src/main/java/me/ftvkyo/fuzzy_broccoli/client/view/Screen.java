package me.ftvkyo.fuzzy_broccoli.client.view;

import me.ftvkyo.fuzzy_broccoli.client.graphics.opengl.ShaderProgram;
import me.ftvkyo.fuzzy_broccoli.common.model.World;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;


public class Screen implements AutoCloseable {

    private long windowGLFW;

    private Drawable currentDrawable;

    private ShaderProgram program;


    public Screen(@NotNull Drawable drawable) {
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

        windowGLFW = glfwCreateWindow(800, 600, "fuzzy-broccoli", NULL, NULL);
        if(windowGLFW == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwMakeContextCurrent(windowGLFW);
        GL.createCapabilities();

        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        try(InputStream vertexShader = cl.getResourceAsStream(ShaderProgram.defaultVertexShaderResource);
            InputStream fragmentShader = cl.getResourceAsStream(ShaderProgram.defaultFragmentShaderResource)) {
            assert vertexShader != null;
            assert fragmentShader != null;

            Scanner vertexS = new java.util.Scanner(vertexShader).useDelimiter("\\A");
            Scanner fragmentS = new java.util.Scanner(fragmentShader).useDelimiter("\\A");

            program = new ShaderProgram(vertexS.next(), fragmentS.next()).use();
        } catch(IOException e) {
            throw new RuntimeException("Could not open shader file.", e);
        }

        this.currentDrawable = drawable;
        currentDrawable.init(program);

        glfwSetFramebufferSizeCallback(windowGLFW, (window, width, height) -> {
            glViewport(0, 0, width, height);
        });

        glfwSetWindowSizeCallback(windowGLFW, (window, width, height) -> {
            glViewport(0, 0, width, height);
        });

        glfwSwapInterval(1);
        glfwShowWindow(windowGLFW);

        //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }


    public void redraw(World currentWorld) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        currentDrawable.draw(currentWorld);

        int err = glGetError();
        if(err != GL_NO_ERROR) {
            throw new RuntimeException("GL Error: " + err);
        }

        glfwSwapBuffers(windowGLFW);
    }


    public void changeCurrentDrawable(@NotNull Drawable drawable) {
        currentDrawable.clear();
        currentDrawable = drawable;

        currentDrawable.init(program);
    }


    public long getWindowGLFW() {
        return windowGLFW;
    }


    public boolean shouldClose() {
        return glfwWindowShouldClose(windowGLFW);
    }


    @Override
    public void close() {
        glfwFreeCallbacks(windowGLFW);
        glfwDestroyWindow(windowGLFW);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}
