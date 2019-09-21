package me.ftvkyo.fuzzy_broccoli.client;

import me.ftvkyo.fuzzy_broccoli.client.mvc.controller.ManagerForController;
import me.ftvkyo.fuzzy_broccoli.client.mvc.model.ManagerForModel;
import me.ftvkyo.fuzzy_broccoli.client.mvc.view.ManagerForView;
import me.ftvkyo.fuzzy_broccoli.common.ArgumentProcessor;


/**
 * Manager for MVC: World(s), Drawable(s) and InputProcessor(s).
 * Drawables are managed through Screen.
 * Worlds are managed through WorldManager.
 * InputProcessors are managed through InputManager.
 */
public class Main implements AutoCloseable {

    private static final int FPS = 60;

    private static final int MSPF = 1000 / FPS;

    private ManagerForModel modelManager;

    private ManagerForView viewManager;

    private ManagerForController controllerManager;


    private Main() {
        this.modelManager = new ManagerForModel();
        this.viewManager = new ManagerForView().setView("main-menu");
        this.controllerManager = new ManagerForController(viewManager.getWindowGLFW(), viewManager, modelManager).setController("main-menu");
    }


    public static void main(String[] args) {
        final String PREFIX = " [client] ";

        ArgumentProcessor.printAll(PREFIX, args);

        try(Main w = new Main()) {
            w.run();
        }

        System.out.println(PREFIX + "Done!");
    }


    private void run() {
        try {
            while(!viewManager.shouldClose()) {
                this.modelManager.update();
                this.viewManager.redraw(modelManager);
                this.controllerManager.pollEvents();

                Thread.sleep(MSPF);
            }
        } catch(InterruptedException e) {
            System.out.println("Thread was interrupted.");
        }
    }


    @Override
    public void close() {
        modelManager.close();
        viewManager.close();
    }
}
