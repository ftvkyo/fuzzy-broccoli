package me.ftvkyo.fuzzy_broccoli.common.mvc;

import me.ftvkyo.fuzzy_broccoli.common.Float3d;
import me.ftvkyo.fuzzy_broccoli.common.notification.INotificationReceiver;


public interface IModel {

    long getTicksPerSecond();


    void tick();


    Float3d getState();


    void setNotificationReceiver(INotificationReceiver nr);
}
