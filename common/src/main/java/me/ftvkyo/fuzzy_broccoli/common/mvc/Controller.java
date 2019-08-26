package me.ftvkyo.fuzzy_broccoli.common.mvc;


import me.ftvkyo.fuzzy_broccoli.common.event.IEvent;
import me.ftvkyo.fuzzy_broccoli.common.notification.INotification;


public class Controller implements IController {

    private static final long VIEW_FRAMES_PER_S = 60;

    private static final long VIEW_MS_PER_FRAME = 1000 / VIEW_FRAMES_PER_S;

    private final long MODEL_TICKS_PER_S;

    private final long MODEL_MS_PER_TICK;

    private final IModel model;

    private final IView view;

    private boolean shouldStop = false;

    private boolean shouldReloadModel = true;


    public Controller(IModel model, IView view) {
        this.model = model;
        this.view = view;

        model.setNotificationReceiver(this);
        view.setEventListener(this);

        MODEL_TICKS_PER_S = model.getTicksPerSecond();
        MODEL_MS_PER_TICK = 1000 / MODEL_TICKS_PER_S;
    }


    @Override
    public void listen(IEvent event) {
        if(event.getInfo().equals("exit")) {
            shouldStop = true;
        }
    }


    @Override
    public void notify(INotification notification) {
        if(notification.getInfo().equals("updated")) {
            shouldReloadModel = true;
        }
    }


    @Override
    public void start() {
        Thread modelThread = new Thread(() -> {
            while(!shouldStop) {
                model.tick();

                try {
                    Thread.sleep(MODEL_MS_PER_TICK);
                } catch(InterruptedException e) {
                    shouldStop = true;
                }
            }
        });
        modelThread.setName("Model");

        Thread viewThread = new Thread(() -> {
            view.init();

            while(!shouldStop) {
                if(shouldReloadModel) {
                    shouldReloadModel = false;
                    view.update(model.getState());
                }
                view.redraw();

                try {
                    Thread.sleep(VIEW_MS_PER_FRAME);
                } catch(InterruptedException e) {
                    shouldStop = true;
                }
            }
        });
        viewThread.setName("View");

        modelThread.start();
        viewThread.start();


        try {
            modelThread.join();
        } catch(InterruptedException ignored) {
        }

        try {
            viewThread.join();
        } catch(InterruptedException ignored) {
        }
    }
}
