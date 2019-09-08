package me.ftvkyo.fuzzy_broccoli.client;

import me.ftvkyo.fuzzy_broccoli.client.controller.Window;
import me.ftvkyo.fuzzy_broccoli.common.ArgumentProcessor;


public class Main {

    private static final String PREFIX = " [client] ";


    public static void main(String[] args) {
        ArgumentProcessor.printAll(PREFIX, args);

        try(Window w = new Window()) {
            w.run();
        }

        System.out.println(PREFIX + "Done!");
    }
}
