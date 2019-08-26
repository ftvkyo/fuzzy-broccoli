package me.ftvkyo.fuzzy_broccoli.common.event;

public class EventStop implements IEvent {

    public EventStop() {
    }


    @Override
    public String getInfo() {
        return "exit";
    }
}
