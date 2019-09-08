package me.ftvkyo.fuzzy_broccoli.client.controller;

import me.ftvkyo.fuzzy_broccoli.client.view.DrawableGame;
import me.ftvkyo.fuzzy_broccoli.client.view.DrawableMainMenu;
import me.ftvkyo.fuzzy_broccoli.client.view.Screen;
import me.ftvkyo.fuzzy_broccoli.common.model.World;

import static org.lwjgl.glfw.GLFW.*;


public class InputProcessorPauseMenu implements InputProcessor {

    private final Screen screen;

    private final World world;


    InputProcessorPauseMenu(Screen screen, World world) {
        this.screen = screen;
        this.world = world;
    }


    public void keypress(long window, long key, long scancode, long action, long mods) {
        if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            screen.changeCurrentDrawable(DrawableGame.getInstance());
            InputProcessor.setForWindow(window, new InputProcessorGame(screen, world));
        }

        if(key == GLFW_KEY_ENTER && action == GLFW_RELEASE) {
            screen.changeCurrentDrawable(DrawableMainMenu.getInstance());
            InputProcessor.setForWindow(window, new InputProcessorMainMenu(screen));
        }
    }


    public void mouseClick(long window, long button, long action, long mods) {

    }


    public void mouseMove(long window, double xpos, double ypos) {

    }
}
