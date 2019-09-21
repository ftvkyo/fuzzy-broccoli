package me.ftvkyo.fuzzy_broccoli.client.mvc.controller;

import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel;
import me.ftvkyo.fuzzy_broccoli.client.mvc.view.ManagerForView;


/**
 * InputProcessor is the "Controller" part in MVC.
 * It provides callbacks for different user input actions.
 */
abstract class Controller {

    final ManagerForView viewManager;

    final ManagerForModel modelManager;

    final ManagerForController controllerManager;


    Controller(ManagerForView viewManager, ManagerForModel modelManager, ManagerForController controllerManager) {
        this.viewManager = viewManager;
        this.modelManager = modelManager;
        this.controllerManager = controllerManager;
    }


    public void init(long window) {
    }


    public void terminate(long window) {
    }


    /**
     * Callback for keyboard user input.
     *
     * @param window   GLSL window of input action.
     * @param key      The keyboard key that was pressed or released.
     * @param scancode TODO
     * @param action   Whether the key was pressed or released.
     * @param mods     Modifiers that were held while action.
     */
    public void keypress(long window, long key, long scancode, long action, long mods) {
    }


    /**
     * Callback for mouse click.
     *
     * @param window GLSL window of input action.
     * @param button The mouse button that was pressed or released.
     * @param action Whether the button was pressed or released.
     * @param mods   Modifiers that were held while action.
     */
    public void mouseClick(long window, long button, long action, long mods) {
    }


    /**
     * Callback for mouse movements.
     *
     * @param window    GLSL window of input action.
     * @param xPosition New position of cursor (x coordinate).
     * @param yPosition New position of cursor (y coordinate).
     */
    public void mouseMove(long window, double xPosition, double yPosition) {
    }
}
