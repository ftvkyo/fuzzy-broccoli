package me.ftvkyo.fuzzy_broccoli.client.controller;

import me.ftvkyo.fuzzy_broccoli.client.view.DrawablePauseMenu;
import me.ftvkyo.fuzzy_broccoli.client.view.Screen;
import me.ftvkyo.fuzzy_broccoli.common.model.WorldManager;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;


/**
 * Input processor that should be used with DrawableGame.
 */
public class InputProcessorGame extends InputProcessor {

    InputProcessorGame(Screen screen, WorldManager worldManager, InputManager inputManager) {
        super(screen, worldManager, inputManager);
    }


    @Override
    public void keypress(long window, long key, long scancode, long action, long mods) {
        if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            screen.changeCurrentDrawable(DrawablePauseMenu.getInstance());
            this.inputManager.setInputProcessor(new InputProcessorPauseMenu(screen, worldManager, inputManager));
        }
    }


    @Override
    public void mouseClick(long window, long button, long action, long mods) {

    }


    @Override
    public void mouseMove(long window, double xPosition, double yPosition) {

    }
}
