package me.ftvkyo.fuzzy_broccoli.common.notification;

public class NotificationUpdated implements INotification {

    @Override
    public String getInfo() {
        return "updated";
    }
}
