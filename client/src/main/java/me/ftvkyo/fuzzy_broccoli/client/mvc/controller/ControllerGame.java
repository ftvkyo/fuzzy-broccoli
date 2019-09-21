package me.ftvkyo.fuzzy_broccoli.client.mvc.controller;

import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel;
import me.ftvkyo.fuzzy_broccoli.client.mvc.view.ManagerForView;
import me.ftvkyo.fuzzy_broccoli.common.mvc.model.parts.Creature;

import static org.lwjgl.glfw.GLFW.*;


/**
 * Input processor that should be used with DrawableGame.
 */
class ControllerGame extends Controller {

    ControllerGame(ManagerForView viewManager, ManagerForModel modelManager, ManagerForController controllerManager) {
        super(viewManager, modelManager, controllerManager);
    }


    @Override
    public void keypress(long window, long key, long scancode, long action, long mods) {
        if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            this.viewManager.setView("pause-menu");
            this.controllerManager.setController("pause-menu");
        }
    }


    @Override
    public void init(long window) {
        super.init(window);
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        glfwSetCursorPos(window, viewManager.getWindowWidth() / 2.0, viewManager.getWindowHeight() / 2.0);
    }


    @Override
    public void terminate(long window) {
        super.terminate(window);
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }


    @Override
    public void mouseMove(long window, double xPosition, double yPosition) {
        Creature player = modelManager.getModel().getPlayers().get(modelManager.getModel().getPlayerName());
        player.getView().x -= (viewManager.getWindowWidth() / 2.0 - xPosition) / 10;
        player.getView().y -= (viewManager.getWindowHeight() / 2.0 - yPosition) / 10;
        glfwSetCursorPos(window, viewManager.getWindowWidth() / 2.0, viewManager.getWindowHeight() / 2.0);
    }
}
