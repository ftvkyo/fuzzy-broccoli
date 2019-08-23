package me.ftvkyo.fuzzy_broccoli.server;

import me.ftvkyo.fuzzy_broccoli.common.ArgumentProcessor;

public class Server {
    private static final String PREFIX = " [server] ";

    public static void main(String[] args) {
        ArgumentProcessor.printAll(PREFIX, args);
        System.out.println(PREFIX + "Done!");
    }
}
