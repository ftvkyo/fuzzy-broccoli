package me.ftvkyo.fuzzy_broccoli.client.mvc.controller

import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel
import me.ftvkyo.fuzzy_broccoli.client.mvc.view.ManagerForView


/**
 * InputProcessor is the "Controller" part in MVC.
 * It provides callbacks for different user input actions.
 */
internal abstract class Controller(val viewManager: ManagerForView, val modelManager: ManagerForModel, val controllerManager: ManagerForController) {


    open fun init(window: Long) {}


    open fun terminate(window: Long) {}


    /**
     * Callback for keyboard user input.
     *
     * @param window   GLSL window of input action.
     * @param key      The keyboard key that was pressed or released.
     * @param scancode TODO
     * @param action   Whether the key was pressed or released.
     * @param mods     Modifiers that were held while action.
     */
    open fun keypress(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {}


    /**
     * Callback for mouse click.
     *
     * @param window GLSL window of input action.
     * @param button The mouse button that was pressed or released.
     * @param action Whether the button was pressed or released.
     * @param mods   Modifiers that were held while action.
     */
    fun mouseClick(window: Long, button: Int, action: Int, mods: Int) {}


    /**
     * Callback for mouse movements.
     *
     * @param window    GLSL window of input action.
     * @param xPosition New position of cursor (x coordinate).
     * @param yPosition New position of cursor (y coordinate).
     */
    open fun mouseMove(window: Long, xPosition: Double, yPosition: Double) {}
}
