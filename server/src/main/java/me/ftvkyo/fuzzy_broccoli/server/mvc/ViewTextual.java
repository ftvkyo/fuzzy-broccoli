package me.ftvkyo.fuzzy_broccoli.server.mvc;

import me.ftvkyo.fuzzy_broccoli.common.Float3d;
import me.ftvkyo.fuzzy_broccoli.common.event.IEventListener;
import me.ftvkyo.fuzzy_broccoli.common.mvc.IView;


public class ViewTextual implements IView {

    @Override
    public void init() {

    }


    @Override
    public void redraw() {

    }


    @Override
    public void update(Float3d value) {
        System.out.println("New state: "
                + value.getV1() + ", "
                + value.getV2() + ", "
                + value.getV3());
    }


    @Override
    public void setEventListener(IEventListener el) {

    }


    @Override
    public void close() throws Exception {

    }
}
