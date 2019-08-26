package me.ftvkyo.fuzzy_broccoli.common.mvc;

import me.ftvkyo.fuzzy_broccoli.common.Float3d;
import me.ftvkyo.fuzzy_broccoli.common.event.IEventListener;


public interface IView extends AutoCloseable {

    void init();


    void redraw();


    void update(Float3d value);


    void setEventListener(IEventListener el);
}
