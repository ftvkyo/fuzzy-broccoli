package me.ftvkyo.fuzzy_broccoli.client.mvc.controller;

import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel;
import me.ftvkyo.fuzzy_broccoli.client.mvc.view.ManagerForView;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;


public class ManagerForController {

    private long windowGLFW;

    private Controller currentController;

    private Map<String, Controller> availableControllers = new HashMap<>();

    private ManagerForView mv;


    public ManagerForController(long window, ManagerForView mv, ManagerForModel mm) {
        this.windowGLFW = window;
        this.mv = mv;

        availableControllers.put("main-menu", new ControllerMainMenu(mv, mm, this));
        availableControllers.put("pause-menu", new ControllerPauseMenu(mv, mm, this));
        availableControllers.put("game", new ControllerGame(mv, mm, this));
    }


    /**
     * Set callbacks of given InputProcessor for given Window.
     *
     * @param controllerName implementation of InputProcessor.
     */
    public ManagerForController setController(@NotNull String controllerName) {
        this.currentController = availableControllers.get(controllerName);

        if(this.currentController != null) {
            glfwSetKeyCallback(this.windowGLFW, this.currentController::keypress);
            glfwSetMouseButtonCallback(this.windowGLFW, this.currentController::mouseClick);
            glfwSetCursorPosCallback(this.windowGLFW, this.currentController::mouseMove);
        } else {
            nglfwSetKeyCallback(this.windowGLFW, 0);
            nglfwSetMouseButtonCallback(this.windowGLFW, 0);
            nglfwSetCursorPosCallback(this.windowGLFW, 0);
        }

        glfwSetFramebufferSizeCallback(windowGLFW, (window, width, height) -> {
            mv.updateWindowSize(width, height);
            mv.resetViewport();
        });

        return this;
    }


    /**
     * Check if there are pending GLFW input events and call corresponding callbacks.
     */
    public void pollEvents() {
        glfwPollEvents();
    }
}
