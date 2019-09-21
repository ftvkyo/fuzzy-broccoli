package me.ftvkyo.fuzzy_broccoli.client.mvc.controller

import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel
import me.ftvkyo.fuzzy_broccoli.client.mvc.view.ManagerForView
import org.lwjgl.glfw.GLFW.*
import java.util.*


class ManagerForController(private val windowGLFW: Long, private val mv: ManagerForView, mm: ManagerForModel) {

    private var currentController: Controller? = null

    private val availableControllers = HashMap<String, Controller>()


    init {

        availableControllers["main-menu"] = ControllerMainMenu(mv, mm, this)
        availableControllers["pause-menu"] = ControllerPauseMenu(mv, mm, this)
        availableControllers["game"] = ControllerGame(mv, mm, this)
    }


    /**
     * Set callbacks of given InputProcessor for given Window.
     *
     * @param controllerName implementation of InputProcessor.
     */
    fun setController(controllerName: String): ManagerForController {
        if (this.currentController != null) {
            this.currentController!!.terminate(this.windowGLFW)
        }
        this.currentController = availableControllers[controllerName]

        if (this.currentController != null) {
            this.currentController!!.init(this.windowGLFW)
            glfwSetKeyCallback(this.windowGLFW) { window, key, scancode, action, mods -> this.currentController!!.keypress(window, key, scancode, action, mods) }
            glfwSetMouseButtonCallback(this.windowGLFW) { window, button, action, mods -> this.currentController!!.mouseClick(window, button, action, mods) }
            glfwSetCursorPosCallback(this.windowGLFW) { window, xPosition, yPosition -> this.currentController!!.mouseMove(window, xPosition, yPosition) }
        } else {
            nglfwSetKeyCallback(this.windowGLFW, 0)
            nglfwSetMouseButtonCallback(this.windowGLFW, 0)
            nglfwSetCursorPosCallback(this.windowGLFW, 0)
        }

        glfwSetFramebufferSizeCallback(windowGLFW) { _, width, height ->
            mv.updateWindowSize(width, height)
            mv.resetViewport()
        }

        return this
    }


    /**
     * Check if there are pending GLFW input events and call corresponding callbacks.
     */
    fun pollEvents() {
        glfwPollEvents()
    }
}
