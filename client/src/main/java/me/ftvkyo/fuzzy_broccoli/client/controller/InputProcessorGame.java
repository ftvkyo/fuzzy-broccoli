package me.ftvkyo.fuzzy_broccoli.client.controller;

import me.ftvkyo.fuzzy_broccoli.client.view.DrawablePauseMenu;
import me.ftvkyo.fuzzy_broccoli.client.view.Screen;
import me.ftvkyo.fuzzy_broccoli.common.model.World;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;


public class InputProcessorGame implements InputProcessor {

    private final Screen screen;

    private final World world;


    InputProcessorGame(Screen screen, World world) {
        this.screen = screen;
        this.world = world;
    }


    public void keypress(long window, long key, long scancode, long action, long mods) {
        if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            screen.changeCurrentDrawable(DrawablePauseMenu.getInstance());
            InputProcessor.setForWindow(window, new InputProcessorPauseMenu(screen, world));
        }
    }


    public void mouseClick(long window, long button, long action, long mods) {

    }


    public void mouseMove(long window, double xpos, double ypos) {

    }
}
