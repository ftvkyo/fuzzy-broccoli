package me.ftvkyo.fuzzy_broccoli.client.controller;

import me.ftvkyo.fuzzy_broccoli.client.view.DrawableGame;
import me.ftvkyo.fuzzy_broccoli.client.view.Screen;
import me.ftvkyo.fuzzy_broccoli.common.model.World;

import java.io.File;

import static org.lwjgl.glfw.GLFW.*;


public class InputProcessorMainMenu implements InputProcessor {

    private final Screen screen;


    InputProcessorMainMenu(Screen screen) {
        this.screen = screen;
    }


    public void keypress(long window, long key, long scancode, long action, long mods) {
        if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window, true);
        }

        if(key == GLFW_KEY_ENTER && action == GLFW_RELEASE) {
            screen.changeCurrentDrawable(DrawableGame.getInstance());
            InputProcessor.setForWindow(window, new InputProcessorGame(screen, new World(new File(""))));
        }
    }


    public void mouseClick(long window, long button, long action, long mods) {

    }


    public void mouseMove(long window, double xpos, double ypos) {

    }
}
