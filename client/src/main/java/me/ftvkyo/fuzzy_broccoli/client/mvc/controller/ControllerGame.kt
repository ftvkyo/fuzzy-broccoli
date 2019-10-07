package me.ftvkyo.fuzzy_broccoli.client.mvc.controller

import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel
import me.ftvkyo.fuzzy_broccoli.client.mvc.view.ManagerForView
import org.joml.Vector2f
import org.lwjgl.glfw.GLFW.*


/**
 * Input processor that should be used with DrawableGame.
 */
internal class ControllerGame(viewManager: ManagerForView, modelManager: ManagerForModel, controllerManager: ManagerForController) : Controller(viewManager, modelManager, controllerManager) {


    override fun keypress(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            this.viewManager.setView("pause-menu")
            this.controllerManager.setController("pause-menu")
        }
    }


    override fun init(window: Long) {
        super.init(window)
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN)
        glfwSetCursorPos(window, viewManager.windowWidth / 2.0, viewManager.windowHeight / 2.0)
    }


    override fun terminate(window: Long) {
        super.terminate(window)
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL)
    }


    override fun mouseMove(window: Long, xPosition: Double, yPosition: Double) {
        val player = modelManager.getModel()!!.getPlayers()[modelManager.getModel()!!.playerName] ?: error("")
        player.view = Vector2f(
                player.view.x - ((viewManager.windowWidth / 2.0 - xPosition) / 10).toFloat(),
                player.view.y - ((viewManager.windowHeight / 2.0 - yPosition) / 10).toFloat())
        glfwSetCursorPos(window, viewManager.windowWidth / 2.0, viewManager.windowHeight / 2.0)
    }
}
