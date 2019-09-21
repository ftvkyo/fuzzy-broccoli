package me.ftvkyo.fuzzy_broccoli.client.mvc.view

import me.ftvkyo.fuzzy_broccoli.client.graphics.opengl.MyGL
import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL33.*
import org.lwjgl.system.MemoryUtil.NULL
import java.util.*


/**
 * Drawable manager.
 * Creates window.
 */
class ManagerForView : AutoCloseable {

    var windowWidth = 800
        private set

    var windowHeight = 600
        private set

    /**
     * Get GLFW id of this Screen's window.
     *
     * @return window id
     */
    val windowGLFW: Long

    private var currentView: View? = null

    private val availableViews = HashMap<String, View>()

    init {
        GLFWErrorCallback.createPrint(System.err).set()

        if (!glfwInit()) {
            throw RuntimeException("Unable to initialize GLFW")
        }

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        windowGLFW = glfwCreateWindow(windowWidth, windowHeight, "fuzzy-broccoli", NULL, NULL)
        if (windowGLFW == NULL) {
            throw RuntimeException("Failed to create the GLFW window")
        }

        glfwMakeContextCurrent(windowGLFW)
        GL.createCapabilities()

        availableViews["main-menu"] = ViewMainMenu()
        availableViews["pause-menu"] = ViewPauseMenu()
        availableViews["game"] = ViewGame()

        glEnable(GL_DEPTH_TEST)
        glfwSwapInterval(1)
        glfwShowWindow(windowGLFW)

        //glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }


    fun updateWindowSize(width: Int, height: Int) {
        this.windowWidth = width
        this.windowHeight = height
    }


    fun resetViewport() {
        glViewport(0, 0, windowWidth, windowHeight)
    }


    /**
     * Redraw screen contents.
     */
    fun redraw(modelManager: ManagerForModel?) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        if (currentView != null) {
            currentView!!.draw(modelManager)
        }

        MyGL.checkError()

        glfwSwapBuffers(windowGLFW)
    }


    /**
     * Substitute current drawable.
     * Perform clean on previous and then initialize the new one.
     *
     * @param viewName new drawable.
     * @return this.
     */
    fun setView(viewName: String): ManagerForView {
        if (currentView != null) {
            currentView!!.clear()
        }

        currentView = availableViews[viewName]

        if (currentView != null) {
            currentView!!.init()
        }

        return this
    }


    /**
     * Check if this Screen's window should close.
     *
     * @return should close this Screen's window
     */
    fun shouldClose(): Boolean {
        return glfwWindowShouldClose(windowGLFW)
    }


    override fun close() {
        glfwFreeCallbacks(windowGLFW)
        glfwDestroyWindow(windowGLFW)

        glfwTerminate()
        glfwSetErrorCallback(null)!!.free()
    }
}
