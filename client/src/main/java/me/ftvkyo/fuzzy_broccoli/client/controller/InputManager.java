package me.ftvkyo.fuzzy_broccoli.client.controller;

import static org.lwjgl.glfw.GLFW.*;


public class InputManager {

    private long windowGLFW;

    private InputProcessor currentInputProcessor;


    public InputManager(long window) {
        this.windowGLFW = window;
    }


    /**
     * Set callbacks of given InputProcessor for given Window.
     *
     * @param inputProcessor implementation of InputProcessor.
     */
    public InputManager setInputProcessor(InputProcessor inputProcessor) {
        this.currentInputProcessor = inputProcessor;

        if(this.currentInputProcessor != null) {
            glfwSetKeyCallback(this.windowGLFW, inputProcessor::keypress);
            glfwSetMouseButtonCallback(this.windowGLFW, inputProcessor::mouseClick);
            glfwSetCursorPosCallback(this.windowGLFW, inputProcessor::mouseMove);
        } else {
            nglfwSetKeyCallback(this.windowGLFW, 0);
            nglfwSetMouseButtonCallback(this.windowGLFW, 0);
            nglfwSetCursorPosCallback(this.windowGLFW, 0);
        }

        return this;
    }


    public void pollEvents() {
        glfwPollEvents();
    }
}
