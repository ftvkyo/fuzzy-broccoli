package me.ftvkyo.fuzzy_broccoli.common.mvc;

import me.ftvkyo.fuzzy_broccoli.common.Float3d;
import me.ftvkyo.fuzzy_broccoli.common.notification.INotificationReceiver;
import me.ftvkyo.fuzzy_broccoli.common.notification.NotificationUpdated;

import java.util.Random;


public class Model implements IModel {

    private final long TICKS_PER_SECOND = 100;

    private INotificationReceiver nr = null;

    private Random random = new Random();

    private Float3d current = new Float3d();

    private Float3d next = new Float3d();

    private Float3d step = new Float3d();

    private long currentStep = TICKS_PER_SECOND;


    public Model() {
    }


    @Override
    public long getTicksPerSecond() {
        return TICKS_PER_SECOND;
    }


    @Override
    public void tick() {
        Float3d localCurrent = current;

        if(currentStep >= TICKS_PER_SECOND) {
            next = new Float3d(random.nextFloat(), random.nextFloat(), random.nextFloat());
            step = next.sub(localCurrent).div(TICKS_PER_SECOND);
            currentStep = 0;
        }

        current = localCurrent.add(step);

        currentStep += 1;

        if(nr != null) {
            nr.notify(new NotificationUpdated());
        }
    }


    @Override
    public Float3d getState() {
        return new Float3d(current);
    }


    @Override
    public void setNotificationReceiver(INotificationReceiver nr) {
        this.nr = nr;
    }
}
