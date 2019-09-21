package me.ftvkyo.fuzzy_broccoli.client.mvc.controller;

import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel;
import me.ftvkyo.fuzzy_broccoli.client.mvc.view.ManagerForView;

import static org.lwjgl.glfw.GLFW.*;


/**
 * Input processor that should be used with DrawablePauseMenu.
 */
class ControllerPauseMenu extends Controller {

    ControllerPauseMenu(ManagerForView viewManager, ManagerForModel modelManager, ManagerForController controllerManager) {
        super(viewManager, modelManager, controllerManager);
    }


    @Override
    public void keypress(long window, long key, long scancode, long action, long mods) {
        if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            this.viewManager.setView("game");
            this.controllerManager.setController("game");
        }

        if(key == GLFW_KEY_ENTER && action == GLFW_RELEASE) {
            this.viewManager.setView("main-menu");
            this.controllerManager.setController("main-menu");
        }
    }
}
