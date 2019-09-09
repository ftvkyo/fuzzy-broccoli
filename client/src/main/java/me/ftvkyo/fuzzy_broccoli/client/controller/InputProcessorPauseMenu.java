package me.ftvkyo.fuzzy_broccoli.client.controller;

import me.ftvkyo.fuzzy_broccoli.client.view.DrawableGame;
import me.ftvkyo.fuzzy_broccoli.client.view.DrawableMainMenu;
import me.ftvkyo.fuzzy_broccoli.client.view.Screen;
import me.ftvkyo.fuzzy_broccoli.common.model.WorldManager;

import static org.lwjgl.glfw.GLFW.*;


/**
 * Input processor that should be used with DrawablePauseMenu.
 */
public class InputProcessorPauseMenu extends InputProcessor {

    InputProcessorPauseMenu(Screen screen, WorldManager worldManager, InputManager inputManager) {
        super(screen, worldManager, inputManager);
    }


    @Override
    public void keypress(long window, long key, long scancode, long action, long mods) {
        if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            screen.changeCurrentDrawable(DrawableGame.getInstance());
            this.inputManager.setInputProcessor(new InputProcessorGame(screen, worldManager, inputManager));
        }

        if(key == GLFW_KEY_ENTER && action == GLFW_RELEASE) {
            screen.changeCurrentDrawable(DrawableMainMenu.getInstance());
            this.inputManager.setInputProcessor(new InputProcessorMainMenu(screen, worldManager, inputManager));
        }
    }


    @Override
    public void mouseClick(long window, long button, long action, long mods) {

    }


    @Override
    public void mouseMove(long window, double xPosition, double yPosition) {

    }
}
