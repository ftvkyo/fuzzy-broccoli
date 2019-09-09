package me.ftvkyo.fuzzy_broccoli.client.controller;

import me.ftvkyo.fuzzy_broccoli.client.view.Screen;
import me.ftvkyo.fuzzy_broccoli.common.model.WorldManager;


/**
 * InputProcessor is a "Controller" part in MVC.
 * It provides callbacks for different user input actions.
 */
public abstract class InputProcessor {

    final Screen screen;

    final WorldManager worldManager;

    final InputManager inputManager;


    InputProcessor(Screen screen, WorldManager worldManager, InputManager inputManager) {
        this.screen = screen;
        this.worldManager = worldManager;
        this.inputManager = inputManager;
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
    void keypress(long window, long key, long scancode, long action, long mods) {
    }


    /**
     * Callback for mouse click.
     *
     * @param window GLSL window of input action.
     * @param button The mouse button that was pressed or released.
     * @param action Whether the button was pressed or released.
     * @param mods   Modifiers that were held while action.
     */
    void mouseClick(long window, long button, long action, long mods) {
    }


    /**
     * Callback for mouse movements.
     *
     * @param window    GLSL window of input action.
     * @param xPosition New position of cursor (x coordinate).
     * @param yPosition New position of cursor (y coordinate).
     */
    void mouseMove(long window, double xPosition, double yPosition) {
    }
}
