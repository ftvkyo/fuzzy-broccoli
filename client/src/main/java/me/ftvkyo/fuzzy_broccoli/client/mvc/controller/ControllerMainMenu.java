package me.ftvkyo.fuzzy_broccoli.client.mvc.controller;

import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel;
import me.ftvkyo.fuzzy_broccoli.client.mvc.view.ManagerForView;
import me.ftvkyo.fuzzy_broccoli.common.mvc.model.Model;

import java.io.File;

import static org.lwjgl.glfw.GLFW.*;


/**
 * Input processor that should be used with DrawableMainMenu.
 */
class ControllerMainMenu extends Controller {

    ControllerMainMenu(ManagerForView viewManager, ManagerForModel modelManager, ManagerForController controllerManager) {
        super(viewManager, modelManager, controllerManager);
    }


    @Override
    public void keypress(long window, long key, long scancode, long action, long mods) {
        if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window, true);
        }

        if(key == GLFW_KEY_ENTER && action == GLFW_RELEASE) {
            this.modelManager.setModel(new Model(new File(""), "player"));
            this.viewManager.setView("game");
            this.controllerManager.setController("game");
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        }
    }


    @Override
    public void mouseClick(long window, long button, long action, long mods) {

    }


    @Override
    public void mouseMove(long window, double xPosition, double yPosition) {

    }
}
