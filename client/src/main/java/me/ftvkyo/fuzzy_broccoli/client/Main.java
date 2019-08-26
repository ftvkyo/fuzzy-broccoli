package me.ftvkyo.fuzzy_broccoli.client;

import me.ftvkyo.fuzzy_broccoli.client.mvc.ViewGraphical;
import me.ftvkyo.fuzzy_broccoli.common.ArgumentProcessor;
import me.ftvkyo.fuzzy_broccoli.common.mvc.Controller;
import me.ftvkyo.fuzzy_broccoli.common.mvc.IModel;
import me.ftvkyo.fuzzy_broccoli.common.mvc.IView;
import me.ftvkyo.fuzzy_broccoli.common.mvc.Model;


public class Main {

    private static final String PREFIX = " [client] ";


    public static void main(String[] args) {
        ArgumentProcessor.printAll(PREFIX, args);

        IModel model = new Model();
        try(IView view = new ViewGraphical()) {
            Controller controller = new Controller(model, view);
            controller.start();
        } catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println(PREFIX + "Done!");
    }
}
