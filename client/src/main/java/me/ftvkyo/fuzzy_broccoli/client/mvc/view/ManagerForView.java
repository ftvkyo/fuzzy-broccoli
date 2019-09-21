package me.ftvkyo.fuzzy_broccoli.client.mvc.view;

import me.ftvkyo.fuzzy_broccoli.client.graphics.opengl.MyGL;
import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.system.MemoryUtil.NULL;


/**
 * Drawable manager.
 * Creates window.
 */
public class ManagerForView implements AutoCloseable {

    private int windowWidth = 800;

    private int windowHeight = 600;

    private long windowGLFW;

    private View currentView;

    private Map<String, View> availableViews = new HashMap<>();


    public ManagerForView() {
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

        windowGLFW = glfwCreateWindow(windowWidth, windowHeight, "fuzzy-broccoli", NULL, NULL);
        if(windowGLFW == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwMakeContextCurrent(windowGLFW);
        GL.createCapabilities();

        availableViews.put("main-menu", new ViewMainMenu());
        availableViews.put("pause-menu", new ViewPauseMenu());
        availableViews.put("game", new ViewGame());

        glEnable(GL_DEPTH_TEST);
        glfwSwapInterval(1);
        glfwShowWindow(windowGLFW);

        //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }


    public void updateWindowSize(int width, int height) {
        this.windowWidth = width;
        this.windowHeight = height;
    }


    public int getWindowWidth() {
        return windowWidth;
    }


    public int getWindowHeight() {
        return windowHeight;
    }


    public void resetViewport() {
        glViewport(0, 0, windowWidth, windowHeight);
    }


    /**
     * Redraw screen contents.
     */
    public void redraw(@Nullable ManagerForModel modelManager) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        if(currentView != null) {
            currentView.draw(modelManager);
        }

        MyGL.checkError();

        glfwSwapBuffers(windowGLFW);
    }


    /**
     * Substitute current drawable.
     * Perform clean on previous and then initialize the new one.
     *
     * @param viewName new drawable.
     * @return this.
     */
    public ManagerForView setView(@NotNull String viewName) {
        if(currentView != null) {
            currentView.clear();
        }

        currentView = availableViews.get(viewName);

        if(currentView != null) {
            currentView.init();
        }

        return this;
    }


    /**
     * Get GLFW id of this Screen's window.
     *
     * @return window id
     */
    public long getWindowGLFW() {
        return windowGLFW;
    }


    /**
     * Check if this Screen's window should close.
     *
     * @return should close this Screen's window
     */
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
