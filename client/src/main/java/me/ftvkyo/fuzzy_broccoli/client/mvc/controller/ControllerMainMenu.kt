package me.ftvkyo.fuzzy_broccoli.client.mvc.controller

import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel
import me.ftvkyo.fuzzy_broccoli.client.mvc.view.ManagerForView
import me.ftvkyo.fuzzy_broccoli.common.mvc.model.Model
import org.lwjgl.glfw.GLFW.*
import java.io.File


/**
 * Input processor that should be used with DrawableMainMenu.
 */
internal class ControllerMainMenu(viewManager: ManagerForView, modelManager: ManagerForModel, controllerManager: ManagerForController) : Controller(viewManager, modelManager, controllerManager) {


    override fun keypress(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window, true)
        }

        if (key == GLFW_KEY_ENTER && action == GLFW_RELEASE) {
            this.modelManager.setModel(Model(File(""), "player"))
            this.viewManager.setView("game")
            this.controllerManager.setController("game")
        }
    }
}
