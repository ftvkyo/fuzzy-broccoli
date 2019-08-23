package me.ftvkyo.fuzzy_broccoli.common;

public class ArgumentProcessor {

    public static void printAll(String prefix, String[] args) {
        for(int i = 0; i < args.length; i++) {
            System.out.print(prefix);
            System.out.println("Argument[" + i + "]: " + args[i]);
        }
    }
}
