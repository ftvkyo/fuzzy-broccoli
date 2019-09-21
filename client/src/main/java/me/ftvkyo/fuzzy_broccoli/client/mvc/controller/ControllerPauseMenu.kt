package me.ftvkyo.fuzzy_broccoli.client.mvc.controller

import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel
import me.ftvkyo.fuzzy_broccoli.client.mvc.view.ManagerForView

import org.lwjgl.glfw.GLFW.*


/**
 * Input processor that should be used with DrawablePauseMenu.
 */
internal class ControllerPauseMenu(viewManager: ManagerForView, modelManager: ManagerForModel, controllerManager: ManagerForController) : Controller(viewManager, modelManager, controllerManager) {


    override fun keypress(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            this.viewManager.setView("game")
            this.controllerManager.setController("game")
        }

        if (key == GLFW_KEY_ENTER && action == GLFW_RELEASE) {
            this.viewManager.setView("main-menu")
            this.controllerManager.setController("main-menu")
        }
    }
}
