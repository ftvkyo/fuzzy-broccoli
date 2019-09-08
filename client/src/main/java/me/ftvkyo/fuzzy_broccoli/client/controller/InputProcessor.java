package me.ftvkyo.fuzzy_broccoli.client.controller;

import static org.lwjgl.glfw.GLFW.*;


public interface InputProcessor {

    static void setForWindow(long window, InputProcessor inputProcessor) {
        glfwSetKeyCallback(window, inputProcessor::keypress);
        glfwSetMouseButtonCallback(window, inputProcessor::mouseClick);
        glfwSetCursorPosCallback(window, inputProcessor::mouseMove);
        //glfwSetWindowSizeCallback(window, inputProcessor::windowResize);
    }


    void keypress(long window, long key, long scancode, long action, long mods);


    void mouseClick(long window, long button, long action, long mods);


    void mouseMove(long window, double xpos, double ypos);


    //void windowResize(long window, long width, long height);
}
