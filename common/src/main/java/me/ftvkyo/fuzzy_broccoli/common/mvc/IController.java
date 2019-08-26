package me.ftvkyo.fuzzy_broccoli.common.mvc;

import me.ftvkyo.fuzzy_broccoli.common.event.IEventListener;
import me.ftvkyo.fuzzy_broccoli.common.notification.INotificationReceiver;


public interface IController extends IEventListener, INotificationReceiver {

    void start();
}
