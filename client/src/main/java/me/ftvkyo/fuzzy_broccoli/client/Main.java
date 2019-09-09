package me.ftvkyo.fuzzy_broccoli.client;

import me.ftvkyo.fuzzy_broccoli.client.controller.InputManager;
import me.ftvkyo.fuzzy_broccoli.client.controller.InputProcessor;
import me.ftvkyo.fuzzy_broccoli.client.controller.InputProcessorMainMenu;
import me.ftvkyo.fuzzy_broccoli.client.view.DrawableMainMenu;
import me.ftvkyo.fuzzy_broccoli.client.view.Screen;
import me.ftvkyo.fuzzy_broccoli.common.ArgumentProcessor;
import me.ftvkyo.fuzzy_broccoli.common.model.WorldManager;


/**
 * Manager for MVC: World(s), Drawable(s) and InputProcessor(s).
 * Drawables are managed through Screen.
 * Worlds are managed through WorldManager.
 * InputProcessors are managed through InputManager.
 */
public class Main implements AutoCloseable {

    private static final int FPS = 60;

    private static final int MSPF = 1000 / FPS;

    private WorldManager worldManager;

    private Screen screen;

    private InputManager inputManager;


    private Main() {
        this.worldManager = new WorldManager();
        this.screen = new Screen(DrawableMainMenu.getInstance());
        this.inputManager = new InputManager(screen.getWindowGLFW());

        InputProcessor ip = new InputProcessorMainMenu(this.screen, this.worldManager, this.inputManager);
        this.inputManager.setInputProcessor(ip);
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
            while(!screen.shouldClose()) {
                this.worldManager.update();
                this.screen.redraw(worldManager.getWorld());
                this.inputManager.pollEvents();

                Thread.sleep(MSPF);
            }
        } catch(InterruptedException e) {
            System.out.println("Thread was interrupted.");
        }
    }


    @Override
    public void close() {
        worldManager.close();
        screen.close();
    }
}
